## Disclaimer: 
I, Matt(`schematical`) am NOT an academic. Writing papers is NOT my strong point. Writing code is. 
Please keep that in mind as you read this. I am sure I will refer to things in terms that would differ from what most data scientists would.  


## v0.1 - Q1 2018
Built with [Mineflayer](https://github.com/PrismarineJS/mineflayer) in NodeJS as a socket server

Note back then I did not realize I would need to have 3+ versions of this so the versioning in the first set of videos is weird



## v0.2 - Q1 2019
Built as a mod into Minecraft v1.13.2 primarily focused on single player.
### Neuro-Evolution of Augmenting Topologies:


### ChaosNet:
The backend uses [ChaosNet](http://chaoslabs.schematical.com/) a web service that is intended to generate NEAT neural networks and act as a Github for NEAT NeuralNets.
ChaosNet allows for multiple SimModels. A SimModel is basically a training environment definition. 3rd party developers have wired other games successfully such as pong.

ChaosNet is run on Amazon Web Services primarily utilizing AWS Lambdas, ElastiCache, APIGateway, AWS Cognito and many other services

ChaosNet automatically does the mutation and breeding that allow NEAT NeuralNetworks to evolve generation after generation. 
#### TrainingRooms:
Anyone that signs up for ChaosNet can create their own TrainingRooms. 
A TrainingRoom is a set of fitness rules that allow the user to customize the bot behavior they wish to train.
Each TrainingRoom has its own TaxonomicRanks(Species) and they have their own collection of Orgs/NNets.

Users(3rd parties I have never met) have built TrainingRooms without coding experience to do a wide variety of tasks:
- Building Structures
- Resource Gathering and Crafting
- Hunting
- Battle Royal
- Hunt Players(against my better judgment)

### Activators:
At this point we implemented Sigmoid Activators

### Biological Traits:
We added several types of biological traits
#### Eyes:
Eyes are raycast out and allow the Orgs to see what blocks they are looking at

#### Memory Slots:
Slots where they could store values for future use as inputs.

#### Unique Skin Colors:
We would use this to generate MineCraft Skins. 

### Prediction:
We added in a reward prediction neuron that allows the orgs to amplify their score reward or punishment if they could predict the reward or punishment.
The value of the prediction output would be fed back in as an input.


### NEAT Observable - "NEATO":
Putting in inputs and outputs for every item, entity, and block type when a species is just starting out has some problems.
An org does not need a neuron to craft diamond pickaxe gen 1 as it is unlikely in its 15-30 second life that it will encounter it. 
Even a human player starting out in Minecraft may not be able to do that for hours.

NEATO allows the NNet to add in neurons only for blocks, items, and entities that other orgs in the species had observed in previous generations. 
This allows them to behave more like a player playing the game for the first time would. You interact only with what you observe.

Here is a video on it: https://www.youtube.com/watch?v=PrVEZwNIK4g



## v0.3 - Q1 2020:
Upgraded the Minecraft Mod to v0.15.2. Massive breaking changes made us start over in a lot of ways.

### Multiplayer:
We focused on multiplayer first allowing TrainingRoom owners to effectively crowdsource CPU power

### TargetNNet:
Because having a neuron for each block, entity, and item type for all the blocks surrounding the Org would require an astronomical amount of neurons we created what I am told is a feature extractor.
When an Org decides to scan the TargetNNet ticks out of sync with the DefaultNNet. It is fed in all the block/entity states around it and assigns it a score. The highest score is assigned as the Target.
#### TargetSlots:
Additionally we added another Biological trait called a TargetSlot. The idea behind this was that we would allow them to target more than one type. 
The state data from the selected target for each TargetSlot is fed into the default nNet allowing it to make decisions based on what is targeted.

#### Craft/Item NNets:
We did the same thing with selecting items in the inventory or recipes to craft.

### Action Buffer:
We added in more specific actions that the orgs could decide to take. 
- PlaceBlock (BlockPos, InventorySlot)
- UseItem (BlockPos, InventorySlot)
- MeleeAttack (Entity, InventorySlot)
- DigBlock (BlockPos, InventorySlot)

This has several benefits:
- Bots take more focused actions
- We can store the actions and the results of those actions so they can be fed back into the neural networks giving them a memory of what they have done so far in the game.
- We can share the results of those actions with other orgs in the area g
- We greatly reduce server traffic because the server will continue carrying out the action on the orgs behalf until it is completed, failed or the org choses to interrupt the action.


### Subtraction Mode:
We found that neural networks were getting too complex to send in their current form and had many neurons and dependencies that were not necessary for the orgs to accomplish their tasks. 
We put a ceiling on how complex a neural net could get before we would start to remove neurons at random trying to remove the ones that are unnecessary. 
More information that can be found here: https://www.youtube.com/watch?v=PI5JS4pN-oA
### Discovery Scoring:
We created a new way of scoring an org called DiscoveryMode. Discovery mode rewards a bot for completing a unique action. 
So for example the first time they craft a crafting table, or the first time the successfully mine diamond.


## v0.3+ - The Future:

### NavigationNNet:
Currently they have trouble navigating. I intend to make an A Start(A*) style NNet that will decide how they travel the world that will tick out of sync with the Default NNet similar to the TargetNNet. 
It will select a path for the action to follow as it traverses the world.

### rtNEAT:
I intend to do some form of "Real Time NEAT"(rtNeat) implementation so the Orgs can evolve new neurons and behaviors in real time without needing to pull the bot out of the world then create mutated children and put them back in starting over from with a completely fresh state.

### NEATOv2/3:
I intend to extend the rtNEAT implementation to work seamlessly with my NEATO Implementation. This has several phases that I am still working on the details for.

### Org to Org Communication:
I want the orgs to be able to pass information back and forth to create more complex in depth group behavior.





