package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.TrainingRoomRoleHolder;
import com.schematical.chaoscraft.client.gui.*;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.*;
import com.schematical.chaoscraft.server.ChaosCraftServer;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.services.targetnet.ScanState;
import com.schematical.chaoscraft.tileentity.FactoryTileEntity;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import com.schematical.chaosnet.model.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChaosCraftClient {

    public TrainingRoomSessionNextResponse lastResponse;
    private int ticksSinceLastSpawn;

    protected State state = State.Uninitiated;
    protected String trainingRoomNamespace;
    protected String trainingRoomUsernameNamespace;
    protected String sessionNamespace;
    protected String env = "pre-alpha";
    public HashMap<String, TrainingRoomRoleHolder> trainingRoomRoles = new HashMap<>();


    public int consecutiveErrorCount = 0;
    public HashMap<String, ClientOrgManager> newOrganisms = new HashMap<String, ClientOrgManager>();
    public HashMap<String, ClientOrgManager> myOrganisms = new HashMap<String, ClientOrgManager>();
    public Thread thread;
    public static List<KeyBinding> keyBindings = new ArrayList<KeyBinding>();
    private int ticksRequiredToCallChaosNet = 20 * 15;
    private ChaosCraftServerPlayerInfo.State observationState = ChaosCraftServerPlayerInfo.State.None;
    private Minecraft minecraft;
    private ChaosPlayerNeuronTestScreen chaosPlayerNeuronTestScreen;
    private ChaosObserveOverlayScreen chaosObserveOverlayScreen;
    private ArrayList<iRenderWorldLastEvent> renderListeners = new ArrayList<>();



    public ChaosCraftClient(Minecraft minecraft) {
        this.minecraft = minecraft;
        chaosObserveOverlayScreen = new ChaosObserveOverlayScreen(this.minecraft);

    }
    public void loadTrainingRoomPackage(){


        if(
            this.trainingRoomNamespace == null ||
            this.trainingRoomUsernameNamespace == null
        ){
            ChaosCraft.LOGGER.error("Not enough TrainingRoom Data set");
            return;
        }
        //Load the roles... package this as a single request
        GetUsernameTrainingroomsTrainingroomPackageRequest request = new GetUsernameTrainingroomsTrainingroomPackageRequest();
        request.setTrainingroom(this.trainingRoomNamespace);
        request.setUsername(this.trainingRoomUsernameNamespace);


        try {
            GetUsernameTrainingroomsTrainingroomPackageResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomPackage(request);
            TrainingRoomPackage trainingRoomPackage = result.getTrainingRoomPackage();
            for (TrainingRoomRole role : trainingRoomPackage.getRoles()) {




                TrainingRoomRoleHolder trainingRoomRoleHolder = new TrainingRoomRoleHolder(role);

                trainingRoomRoles.put(role.getNamespace(), trainingRoomRoleHolder);
                state = ChaosCraftClient.State.TrainingRoomPackageLoaded;
            }
            startTrainingSession();

        }catch(ChaosNetException exception) {
            //logger.error(exeception.getMessage());
            ChaosCraft.getServer().consecutiveErrorCount += 1;

            int statusCode = exception.sdkHttpMetadata().httpStatusCode();
            switch (statusCode) {
                case (400):

                    ChaosCraft.getServer().repair();
                    break;
                case (401):
                    ChaosCraft.auth();
                    break;
                case (409):
                    //ChaosCraft.auth();
                    break;
            }
            ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
            String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
            ChaosCraft.LOGGER.error("loadRoles  Error: " + message + " - statusCode: " + statusCode);
            exception.printStackTrace();
        }catch(Exception exception){
            // ChaosCraft.getServer().consecutiveErrorCount += 1;

            ChaosCraft.LOGGER.error("loadRoles Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName());
            // ChaosCraft.getClient().thread = null;//End should cover this


        }

    }
    public ChaosCraftServerPlayerInfo.State getObservationState(){
        return observationState;
    }

    public void setObservationState(ChaosCraftServerPlayerInfo.State observationState){
        this.observationState = observationState;
        if (observationState.equals(ChaosCraftServerPlayerInfo.State.None)) {
            this.minecraft.player.setGameType(GameType.CREATIVE);
        }else{
            this.minecraft.player.setGameType(GameType.SPECTATOR);
        }
    }
    public void onWorldUnload() {
        state = State.Uninitiated;
        myOrganisms.clear();
        renderListeners.clear();
        observationState = ChaosCraftServerPlayerInfo.State.None;

    }
    public void showPlayerNeuronTestScreen(){
        chaosPlayerNeuronTestScreen = new ChaosPlayerNeuronTestScreen(this.minecraft);
    }
    public void render() {

        /*if(chaosPlayerNeuronTestScreen != null){
            chaosPlayerNeuronTestScreen.render();
        }*/
        if(!observationState.equals(ChaosCraftServerPlayerInfo.State.None)){
            chaosObserveOverlayScreen.render();
        }
    }
    public void setTrainingRoomInfo(ServerIntroInfoPacket serverInfo) {
        trainingRoomNamespace = serverInfo.getTrainingRoomNamespace();
        trainingRoomUsernameNamespace = serverInfo.getTrainingRoomUsernameNamespace();

        if(!env.equals(serverInfo.getEnv())){
            env = serverInfo.getEnv();
            ChaosCraft.setupSDK(env);
        }
       //sessionNamespace = serverInfo.getSessionNamespace();
        ChaosCraft.LOGGER.info("TrainingRoomInfo Set: " + trainingRoomNamespace + ", " + trainingRoomUsernameNamespace + ", " + sessionNamespace + " - ENV: " + env);
        state = State.Authed;
        if((Minecraft.getInstance().currentScreen instanceof ChaosTrainingRoomSelectionOverlayGui)) {

            Minecraft.getInstance().displayGuiScreen((Screen)null);
        }
        loadTrainingRoomPackage();
    }
    public HashMap<ScanState, Integer> getScanStateCounts(){
        HashMap<ScanState, Integer> states = new HashMap<>();
        for (ClientOrgManager clientOrgManager : myOrganisms.values()) {
            ScanManager scanManager = clientOrgManager.getScanManager();
            if(scanManager != null) {
                ScanState scanState = scanManager.getState();
                if (!states.containsKey(scanState)) {
                    states.put(scanState, 0);
                }
                states.put(scanState, states.get(scanState) + 1);
            }
        }
        return states;
    }
    public void startTrainingSession(){

    /*    ChaosCraft.config.trainingRoomUsernameNamespace = trainingRoomUsernameNamespace;
        ChaosCraft.config.trainingRoomNamespace = trainingRoomNamespace;
        ChaosCraft.config.save();*/

        try {
            PostUsernameTrainingroomsTrainingroomSessionsStartRequest startSessionRequest = new PostUsernameTrainingroomsTrainingroomSessionsStartRequest();
            startSessionRequest.setTrainingroom(trainingRoomNamespace);
            startSessionRequest.setUsername(trainingRoomUsernameNamespace);

            PostUsernameTrainingroomsTrainingroomSessionsStartResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsStart(startSessionRequest);
            sessionNamespace = result.getTraningRoomSessionStartResponse().getSession().getNamespace();
            //ChaosCraft.config.save();
            state = State.TrainingRoomSessionStarted;

        }catch(ChaosNetException exception) {
            int statusCode = exception.sdkHttpMetadata().httpStatusCode();
            if (statusCode == 401) {
                ChaosCraft.LOGGER.error(exception.getMessage());
                String message = "Your login has expired. Please re-run `/chaoscraft-auth {username} {password}`";
                //ChaosCraft.chat(message);
                ChaosCraft.LOGGER.error(message);
            }else if(statusCode == 418){

                ChaosAlertOverlay chaosAlertOverlay = new ChaosAlertOverlay("chaoscraft.gui.alert.put-your-session-to-sleep");
                Minecraft.getInstance().displayGuiScreen(chaosAlertOverlay);

            }else{
               /* throw exception;*/
                ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
                String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
                ChaosCraft.LOGGER.error("`ChaosClient.startTrainingSession` Error: " + message + " - statusCode: " + statusCode);

            }

        }/*catch(Exception exception){
            ChaosCraft.getClient().consecutiveErrorCount += 1;

            ChaosCraft.LOGGER.error("ChaosClientThread `startTrainingSession` Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName() + " stack: " + exception.getStackTrace());
            ChaosCraft.getClient().thread = null;
            ChaosCraft.getClient().setTicksRequiredToCallChaosNet(1000);

        }*/

    }

    public State getState() {
        return state;
    }
    public String getTrainingRoomNamespace(){
        return trainingRoomNamespace;
    }
    public String getTrainingRoomUsernameNamespace(){
        return trainingRoomUsernameNamespace;
    }
    public String getSessionNamespace(){
        return sessionNamespace;
    }
    public String getEnv(){
        return env;
    }

    public void preInit(){
        keyBindings.add(new KeyBinding(CCKeyBinding.SHOW_ORG_LIST,79, "key.chaoscraft"));
        keyBindings.add(new KeyBinding(CCKeyBinding.OBSERVER_MODE, 0x18, "key.chaoscraft"));
        keyBindings.add(new KeyBinding(CCKeyBinding.SHOW_SPECIES_LIST, 0x24, "key.chaoscraft"));
 /*       keyBindings.add(new KeyBinding(CCKeyBinding.UP, 87, "key.chaoscraft"));
        keyBindings.add(new KeyBinding(CCKeyBinding.LEFT, 65, "key.chaoscraft"));
        keyBindings.add(new KeyBinding(CCKeyBinding.DOWN, 83, "key.chaoscraft"));
        keyBindings.add(new KeyBinding(CCKeyBinding.RIGHT, 70, "key.chaoscraft"));*/

// register all the key bindings
        for (int i = 0; i < keyBindings.size(); ++i)
        {
            ClientRegistry.registerKeyBinding(keyBindings.get(i));
        }
        if(ChaosCraft.config.username == null){
            if(!(Minecraft.getInstance().currentScreen instanceof ChaosAuthOverlayGui)) {
                ChaosAuthOverlayGui screen = new ChaosAuthOverlayGui();
                Minecraft.getInstance().displayGuiScreen(screen);
            }
            return;
        }
    }
    public void setTicksRequiredToCallChaosNet(int i){
        ticksRequiredToCallChaosNet = i;
    }
    public void init(){
        if(Minecraft.getInstance().getConnection() == null){
            return;
        }

        if(ChaosCraft.config.accessToken == null){
            //MAKE THEM AUTH FIRST
            //but only open the screen when it isnt already open
            if(!(Minecraft.getInstance().currentScreen instanceof ChaosAuthOverlayGui)) {
                ChaosAuthOverlayGui screen = new ChaosAuthOverlayGui();
                Minecraft.getInstance().displayGuiScreen(screen);
            }
            return;
        }


        ChaosCraft.LOGGER.info("Client Sending Auth!!");
        //!!!!!!!
        ChaosNetworkManager.sendToServer(new ClientAuthPacket(ChaosCraft.config.accessToken));
        state = State.AuthSent;
        //Get info on what the server is running


    }

    public void attachOrgToEntity(CCServerEntitySpawnedPacket pkt) {
        OrgEntity orgEntity = (OrgEntity)Minecraft.getInstance().world.getEntityByID(pkt.entityId);
        if(orgEntity == null){
            ChaosCraft.LOGGER.error("Client could not find entityId: " + pkt.entityId + " to attach org: " + pkt.orgNamespace);

            Iterator<Entity> iterator = Minecraft.getInstance().world.getAllEntities().iterator();
            while(iterator.hasNext() && orgEntity == null){
                Entity entity = iterator.next();
                if( entity.getDisplayName().getString().equals(pkt.orgNamespace)) {
                    ChaosCraft.LOGGER.error("Client found a potential match after all entityId: " + entity.getEntityId() + " will attach org: " + pkt.orgNamespace + " == " + entity.getDisplayName().getString());
                    orgEntity = (OrgEntity) entity;
                }


            }
            if(orgEntity == null) {//If it is still null lets drop out
                //orgsQueuedToSpawn.remove(orgNamespace);
                ChaosCraft.LOGGER.error("TODO: Requeue for spawn");// Try the spawn over again. If it fails again then the server will just let us know again which one it is
                return;
            }
        }
        ClientOrgManager clientOrgManager = myOrganisms.get(pkt.orgNamespace);
        clientOrgManager.attachOrgEntity(orgEntity);
        clientOrgManager.setExpectedLifeEndTime(pkt.expectedLifeEndTime);

    }
    public List<ClientOrgManager> getOrgsWithState(ClientOrgManager.State state){
        List<ClientOrgManager> orgManagers = new ArrayList<ClientOrgManager>();
        for (ClientOrgManager clientOrgManager : myOrganisms.values()) {
            if(clientOrgManager.getState().equals(state)){
                orgManagers.add(clientOrgManager);
            }
        }
        return orgManagers;
    }
    public void tick(){

        if(!state.equals(State.TrainingRoomSessionStarted)){
            return;
        }
        /*if(true){
            return;
        }*/
        checkForDeadOrgs();
        startSpawnOrgs();
        int liveOrgCount = getLiveOrgCount();
        ticksSinceLastSpawn += 1;



        //List<ClientOrgManager> deadOrgs = getDeadOrgs();
        List<ClientOrgManager> orgsReadyToReport = getOrgsWithState(ClientOrgManager.State.ReadyToReport);
        if (
            orgsReadyToReport.size() > 0 ||
            (
                ticksSinceLastSpawn > (ticksRequiredToCallChaosNet) &&
                (liveOrgCount) < ChaosCraft.config.maxBotCount
            )
        ) {

            if(thread == null) {
                triggerOnReport(orgsReadyToReport);
                if(newOrganisms.size() > 0){
                    cleanUp();
                    Iterator<String> iterator = newOrganisms.keySet().iterator();
                    while(iterator.hasNext()){
                        String namespace = iterator.next();
                        myOrganisms.put(namespace, newOrganisms.get(namespace));
                        iterator.remove();
                    }
                }
                ticksSinceLastSpawn = 0;
                ticksRequiredToCallChaosNet = 100;
                thread = new Thread(new ChaosClientThread(), "ChaosClientThread");
                thread.start();
            }
        }

        if(consecutiveErrorCount > 5){
            throw new ChaosNetException("ChaosCraft.consecutiveErrorCount > 5");
        }



    }

    private void triggerOnReport(List<ClientOrgManager> orgsReadyToReport) {
        for (ClientOrgManager orgManager : orgsReadyToReport) {
            if(!orgManager.getState().equals(ClientOrgManager.State.ReadyToReport)){
                throw new ChaosNetException("Invalid state: " + orgManager.getState());
            }
            orgManager.triggerOnReport();
        }
    }


    public List<ClientOrgManager> checkForDeadOrgs(){

        List<ClientOrgManager> clientOrgManagers = getOrgsWithState(ClientOrgManager.State.Ticking);
        for (ClientOrgManager clientOrgManager : clientOrgManagers) {

            if (!clientOrgManager.getEntity().isAlive()) {

                clientOrgManager.markDead();

            }
        }
        return clientOrgManagers;
    }
    private int getLiveOrgCount() {

        Iterator<ClientOrgManager> iterator = myOrganisms.values().iterator();
        int liveOrgCount = 0;
        while (iterator.hasNext()) {
            ClientOrgManager clientOrgManager = iterator.next();
            clientOrgManager.manualUpdateCheck();
            if (
                clientOrgManager.getOrganism() == null// ||
                //organism.getSpawnHash() != ChaosCraft.spawnHash
            ) {
                clientOrgManager.getEntity().setHealth(-1);
                iterator.remove();
                //ChaosCraft.logger.info("Setting Dead: " + organism.getName() + " - Has no `Organism` record");
            }
            if (
                clientOrgManager.getEntity() != null &&
                clientOrgManager.getEntity().isAlive()
            ) {
                liveOrgCount += 1;
            }
        }
        return liveOrgCount;

    }

    private void startSpawnOrgs() {

        Iterator<ClientOrgManager> iterator = getOrgsWithState(ClientOrgManager.State.OrgAttached).iterator();

        while (iterator.hasNext()) {
            ClientOrgManager clientOrgManager = iterator.next();


            CCClientSpawnPacket packet = new CCClientSpawnPacket(
                clientOrgManager.getCCNamespace()
            );
            clientOrgManager.markSpawnMessageSent();

            ChaosNetworkManager.sendToServer(packet);

        }
    }



    @SubscribeEvent
    public  void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        //ChaosCraft.LOGGER.info("KeyDonw: " + event.getKey());
        for (KeyBinding keyBinding : keyBindings) {
            // check each enumerated key binding type for pressed and take appropriate action
            if (keyBinding.isPressed()) {
                // DEBUG
                switch(keyBinding.getKeyDescription()){
                    case(CCKeyBinding.SHOW_ORG_LIST):
                        //CCOrgListView view = new CCOrgListView();

                        ChaosInGameMenuOverlayGui screen = new ChaosInGameMenuOverlayGui();
                        Minecraft.getInstance().displayGuiScreen(screen);
                        break;
                    case(CCKeyBinding.SHOW_SPECIES_LIST):
                       /* CCSpeciesListView view2 = new CCSpeciesListView();

                        Minecraft.getInstance().displayGuiScreen(view2);*/
                        break;
                    case(CCKeyBinding.OBSERVER_MODE):
                      /*  List<EntityPlayerMP> players = Minecraft.getMinecraft().world.<EntityPlayerMP>getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
                            @Override
                            public boolean apply(@Nullable EntityPlayerMP input) {
                                return true;
                            }
                        });
                        for(EntityPlayerMP player: players){
                            ChaosCraft.client.toggleObservingPlayer(player);
                        }*/
                        break;
                    case(CCKeyBinding.UP):
                    case(CCKeyBinding.DOWN):
                    case(CCKeyBinding.LEFT):
                    case(CCKeyBinding.RIGHT):

                        break;
                }

                // do stuff for this key binding here
                // remember you may need to send packet to server


            }
        }


    }

    public void attachScoreEventToEntity(CCServerScoreEventPacket message) {
        if(!myOrganisms.containsKey(message.orgNamespace)){
            ChaosCraft.LOGGER.error("attatchScoreEventToEntity - Cannot find orgNamespace: " + message.orgNamespace);
            return;
        }
        myOrganisms.get(message.orgNamespace).addServerScoreEvent(message);
    }

    public int getTicksSinceLastSpawn() {
        return ticksSinceLastSpawn;
    }
    public HashMap<ClientOrgManager.State, ArrayList<ClientOrgManager>> getOrgsSortedByState(){
        HashMap<ClientOrgManager.State, ArrayList<ClientOrgManager>> coll = new HashMap<ClientOrgManager.State, ArrayList<ClientOrgManager>>();
        for (ClientOrgManager clientOrgManager : myOrganisms.values()) {
            if(!coll.containsKey(clientOrgManager.getState())){
                coll.put(clientOrgManager.getState(), new ArrayList<ClientOrgManager>());
            }
            coll.get(clientOrgManager.getState()).add(clientOrgManager);
        }
        return coll;
    }

    public void showOrdDetailOverlay(ClientOrgManager clientOrgManager) {
        ChaosOrgDetailOverlayGui screen = new ChaosOrgDetailOverlayGui(clientOrgManager);
        Minecraft.getInstance().displayGuiScreen(screen);
    }
    public void cleanUp(){
        Iterator<ClientOrgManager> iterator = myOrganisms.values().iterator();
        while(iterator.hasNext()){

            ClientOrgManager clientOrgManager = iterator.next();
            if(clientOrgManager.getState().equals(ClientOrgManager.State.FinishedReport)){
                iterator.remove();
            }
        }
    }
    public void repair(){
        try{

            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest request = new PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest();
            request.setUsername(trainingRoomUsernameNamespace);
            request.setTrainingroom(trainingRoomNamespace);
            request.setSession(sessionNamespace);
            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult response = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionRepair(request);

        }catch(ChaosNetException exception){
            ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
            String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();
            exception.setMessage(message);
            throw exception;
        }
    }

    public void displayTrainingRoomSelectionOverlayGui() {

        ChaosTrainingRoomSelectionOverlayGui screen = new ChaosTrainingRoomSelectionOverlayGui();
        Minecraft.getInstance().displayGuiScreen(screen);
    }

    public void showSpawnBlockGui(SpawnBlockTileEntity tileentity) {
        ChaosSpawnBlockSettingScreen screen = new ChaosSpawnBlockSettingScreen(tileentity);
        ChaosCraft.LOGGER.debug("Showing SPawnBlockGui: " +tileentity.getSpawnPointId());
        //Open up gui
        Minecraft.getInstance().displayGuiScreen(screen);
    }
    public void showFactoryBlockGui(FactoryTileEntity tileentity) {
        ChaosFactoryBlockSettingScreen screen = new ChaosFactoryBlockSettingScreen(tileentity);

        Minecraft.getInstance().displayGuiScreen(screen);
    }

    public void updateObservingEntity(CCServerObserverOrgChangeEventPacket message) {
        ClientOrgManager clientOrgManager = null;
        if(myOrganisms.containsKey(message.orgNamespace)){
            clientOrgManager = myOrganisms.get(message.orgNamespace);
        }
        chaosObserveOverlayScreen.setObservedEntity(message, clientOrgManager);
        if(chaosPlayerNeuronTestScreen != null){
            chaosPlayerNeuronTestScreen.setObservedEntity(message, clientOrgManager);
        }

    }

    public void stir() {
        //Iterate through all creatures

        //Send message to server

    }

    public void attachActionStateChange(CCActionStateChangeEventPacket message) {
        if(!myOrganisms.containsKey(message.orgNamespace)){
            ChaosCraft.LOGGER.error("attatchScoreEventToEntity - Cannot find orgNamespace: " + message.orgNamespace);
            return;
        }
        myOrganisms.get(message.orgNamespace).getActionBuffer().applyStateChange(message);
    }

    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if(chaosObserveOverlayScreen == null){
            return;
        }
        chaosObserveOverlayScreen.onRenderWorldLastEvent(event);
        Iterator<iRenderWorldLastEvent> iterator = renderListeners.iterator();
        while(iterator.hasNext()){
            iRenderWorldLastEvent renderListener = iterator.next();
            boolean keepMe = renderListener.onRenderWorldLastEvent(event);
            if(!keepMe){
                iterator.remove();
            }

        }
    }
    public void addRenderListener(iRenderWorldLastEvent renderListener){
        renderListeners.add(renderListener);
    }
    public void removeRenderListener(iRenderWorldLastEvent renderListener){
        renderListeners.remove(renderListener);
    }


    public ChaosObserveOverlayScreen getObserveOverlayScreen() {
        return chaosObserveOverlayScreen;
    }

    public ClientOrgManager getObservedOrganism() {
        if(chaosObserveOverlayScreen == null){
            return null;
        }
        return chaosObserveOverlayScreen.getObservedEntity();
    }

    public enum State{
        Uninitiated,
        AuthSent,
        TrainingRoomPackageLoaded,
        TrainingRoomSessionStarted, Authed
    }
}
