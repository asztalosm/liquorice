import GameClasses.GameClasses;

import java.util.List;

public class Entities {
    //region NORTH
    public static GameClasses.Entity brute = new GameClasses.Entity("Brute", 5, 5, -1, 1, "ascii-art/axeman.txt", List.of(Weapons.Fists), new String[] {
        "Speaking some sort of unknown language, these typically blonde and white skinned folks seem not too keen on using advanced weaponry such as guns,",
        "though it has been observed that they would rather frequently use modern tactical weaponry that they themselves are probably not able to produce.",
        "",
        "This bloke here however decided to put all his trust in whatever god he believes in into his fists. I almost feel bad for this fellow."
    });
    public static GameClasses.Entity axeman = new GameClasses.Entity("Axeman", 3, 5, 0, 1, "ascii-art/axeman.txt", List.of(Weapons.TacticalAxe), new String[] {
        "Speaking some sort of unknown language, these typically blonde and white skinned folks seem not too keen on using advanced weaponry such as guns,",
        "though it has been observed that they would rather frequently use modern tactical weaponry that they themselves are probably not able to produce.",
        "The presence of these invaders are quite surprising as the Central Executive Council has already explored all of the world as we know it,",
        "to the eternal, suffocating fog that never rises, marking the border to the universe as we know it.",
        "",
        "Now this one is a more formidable warrior, though still not one that would bear any sort of intrigueing weapon of war. Similar to mine, this one",
        "wants to solve all his problems by frantically flinging an axe into my general direction. I wish it was as simple as that too, mate."
    });
    public static GameClasses.Entity witch = new GameClasses.Entity("Witch", 3, 10, 0, 1, "ascii-art/witch.txt", List.of(Weapons.ritualDagger), new String[] {
        "Speaking some sort of unknown language, these typically blonde and white skinned folks seem not too keen on using advanced weaponry such as guns,",
        "though it has been observed that they would rather frequently use modern tactical weaponry that they themselves are probably not able to produce.",
        "The presence of these invaders are quite surprising as the Central Executive Council has already explored all of the world as we know it,",
        "to the eternal, suffocating fog that never rises, marking the border to the universe as we know it.",
        "",
        "Okay now we're getting somewhere. This lady only wields a tiny dagger, as her weapon of choice, however she seems to be onto some unholy",
        "tomfoolery when it comes to combat. The edge of the dagger is forever shiny and forever bright. There is not one drop of blood on that blade.",
        "Coincidentally, the more the blade drinks, the stronger the lady fights. More dangerous, unpredictable, animalistic in nature."
    });
    public static GameClasses.Entity masterWitch = new GameClasses.Entity("Master Witch", 10, 7, 0, 1, "ascii-art/master-witch.txt", List.of(Weapons.masterWitch), new String[] {
        "Speaking some sort of unknown language, these typically blonde and white skinned folks seem not too keen on using advanced weaponry such as guns,",
        "though it has been observed that they would rather frequently use modern tactical weaponry that they themselves are probably not able to produce.",
        "The presence of these invaders are quite surprising as the Central Executive Council has already explored all of the world as we know it,",
        "to the eternal, suffocating fog that never rises, marking the border to the universe as we know it.",
        "",
        "When I stumbled upon this hag, she was doing something rather peculiar. Amidst the towering liquorice plants, she was slicing the throat of our",
        "civilians, which she presumably ordered to be captured as prisoners of war. As their throat split, no blood came out. Only a strange blue",
        "flame - no, rather a stream of bright, blue smoke that burnt through all that it touched. The stream was then directed towards the plants,",
        "turning them into mist within mere seconds, quite literally as the flame left no ashes whatsoever. Needless to say, we're not going to have",
        "a good time together."
    });
    //endregion
    
    //region EAST
    public static GameClasses.Entity spawn = new GameClasses.Entity("Spawn", 5, 3, 0, 2, "ascii-art/spawn.txt", List.of(Weapons.rat), new String[] {
        "Hardly speaking, these... things are humanoid machines of war. While flesh for the most part, yes, they seem to contain a large abundance of",
        "wires, cables and steel rods that indicate a more artificial nature. Still, their face remains human and they still seem to feel the same",
        "way as I do with their cocky smile fading the very second the tide of battle turns the other way.",
        "Genetic samples disprove any sort of relation between the northern invaders and the eastern one. So much so that it is very unlikely that",
        "these two could co-exist in the same society or even just the same habitat. Moreover, upon inspecting their corpses it becomes apparent that",
        "in no possible way could these creatures exist by themselves. An indicator of this is the strange greyish fabric that runs through their flesh.",
        "",
        "This one is more animal than human in nature. With the structure of its legs, it could barely stand on its feet without collapsing.",
        "Naturally, this means that this little bastard walks on all fours. Or rather, runs on all fours, anf it does so with quite the velocity."
    });
    public static GameClasses.Entity manhound = new GameClasses.Entity("Manhound", 8, 3, 1, 2, "ascii-art/manhound.txt", List.of(Weapons.manhound), new String[] {
        "Hardly speaking, these... things are humanoid machines of war. While flesh for the most part, yes, they seem to contain a large abundance of",
        "wires, cables and steel rods that indicate a more artificial nature. Still, their face remains human and they still seem to feel the same",
        "way as I do with their cocky smile fading the very second the tide of battle turns the other way.",
        "Genetic samples disprove any sort of relation between the northern invaders and the eastern one. So much so that it is very unlikely that",
        "these two could co-exist in the same society or even just the same habitat. Moreover, upon inspecting their corpses it becomes apparent that",
        "in no possible way could these creatures exist by themselves. An indicator of this is the strange greyish fabric that runs through their flesh.",
        "",
        "It is difficult to look at this cur. As if many eyes of many many different species were put into the same eye socket, this creature",
        "causes immense discomfort just by looking at it. Whoever made this must have been feeling generous the day he made this diabolical doohickey",
        "because he felt like including electric ports within the canines of the thing, shocking the target with each bite it takes.",
    });
    public static GameClasses.Entity gunner = new GameClasses.Entity("Gunner", 5, 9, 0, 1, "ascii-art/gunner.txt", List.of(Weapons.armCannon), List.of(
        new GameClasses.StatusEffect(Effects.ammo, 3, true)
    ), new String[] {
        "Hardly speaking, these... things are humanoid machines of war. While flesh for the most part, yes, they seem to contain a large abundance of",
        "wires, cables and steel rods that indicate a more artificial nature. Still, their face remains human and they still seem to feel the same",
        "way as I do with their cocky smile fading the very second the tide of battle turns the other way.",
        "Genetic samples disprove any sort of relation between the northern invaders and the eastern one. So much so that it is very unlikely that",
        "these two could co-exist in the same society or even just the same habitat. Moreover, upon inspecting their corpses it becomes apparent that",
        "in no possible way could these creatures exist by themselves. An indicator of this is the strange greyish fabric that runs through their flesh.",
        "",
        "In a weird way it is delightful to finally be able to fight something that is at least able to stand on its feet. This unit is",
        "designed with a highly specific purpose, that being ranged combat. First of all, we may see that it is armed with a firearm mounted in its",
        "dominant arm, completely replacing any sort of hand it might have had otherwise. Its face is asymmetrical, with the right eye being",
        "gigantic and the left one completely missing. Another key factor in its efficiency is the ability to repurpose non- and organic scrap as",
        "ammunition, and quite annoyingly it sometimes does so when the \"organic scrap\" is still inside your body.",
    });
    //endregion
    
    //region WEST
    public static GameClasses.Entity geopolitanTrooper = new GameClasses.Entity("Geop. Trooper", 7, 5, 2, 1, "ascii-art/geopolitan.txt", List.of(Weapons.geopolitanMace), new String[] {
        "A black mask covering the face and a great, thickly woven cloak of mysterious, impenetrable fabric covering the body. These creatures whom we",
        "simply dub as the \"Geopolitans\" are at the very least two meters tall, grey skinned humanoids. They move and act as one constantly, with",
        "such precision that would be impossible to achieve using chemical signals or any other sort of communication we know of. The fabric they",
        "wear is really strange, because not is it only as firm as a plate armor while being as light as a silk robe, but it is the same material that",
        "was used in the construction of the dagger of the Master Witch, or the bodies of the eastern invaders. These two have to be related to the",
        "greyskins in one way or another.",
        "",
        "A simple trooper, equipped with nothing but a sort of a long bludgeoning weapon, akin to a footman's mace and a baton at the same time.",
        "Though this may sound foolish for a solider suited for modern warfare, do not be mistaken. These soliders can evoke some sort of a shield",
        "around themselves, meaning that they are quite effective shocktroopers. It is clear that their greatest use case is overrunning forts and",
        "trenches.",
    });
    public static GameClasses.Entity geopolitanGunner = new GameClasses.Entity("Geop. Gunner", 6, 5, 1, 1, "ascii-art/geopolitan.txt", List.of(Weapons.geopolitanWeaver), List.of(
        new GameClasses.StatusEffect(Effects.ammo, 3, true)
    ), new String[] {        
        "A black mask covering the face and a great, thickly woven cloak of mysterious, impenetrable fabric covering the body. These creatures whom we",
        "simply dub as the \"Geopolitans\" are at the very least two meters tall, grey skinned humanoids. They move and act as one constantly, with",
        "such precision that would be impossible to achieve using chemical signals or any other sort of communication we know of. The fabric they",
        "wear is really strange, because not is it only as firm as a plate armor while being as light as a silk robe, but it is the same material that",
        "was used in the construction of the dagger of the Master Witch, or the bodies of the eastern invaders. These two have to be related to the",
        "greyskins in one way or another.",
        "",
        "Equipped with what may look like a heavy-barrelled pistol at first, these soliders use ranged weapons to combat their opponents. These are",
        "much more miraculous inventions than simple firearms though. Mechanically they resemble a crossbow much more than any gun I've ever seen.",
        "Their ammunition is not just any arrow however, but tiny metal wires that are being shot at the target with at incredible speeds. The point",
        "of these is not to cause damage but to cause exaustion, and kill via internal bleeding at a much more effective cost.",
    });
    public static GameClasses.Entity geopolitanTaskmaster = new GameClasses.Entity("Geop. Taskmaster", 10, 10, 1, 2, "ascii-art/geopolitan-f.txt", List.of(Weapons.geopolitanMace2), List.of(
        new GameClasses.StatusEffect(Effects.badge, 2, true)
    ), new String[] {
        "A black mask covering the face and a great, thickly woven cloak of mysterious, impenetrable fabric covering the body. These creatures whom we",
        "simply dub as the \"Geopolitans\" are at the very least two meters tall, grey skinned humanoids. They move and act as one constantly, with",
        "such precision that would be impossible to achieve using chemical signals or any other sort of communication we know of. They almost all",
        "look alike, acting more like bees of a hive than individuals. The fabric they wear is really strange, because not is it only as firm as plate",
        "armor while being as light as a silk robe, but it is the same material that was used in the construction of the dagger of the Master Witch, or",
        "the bodies of the eastern invaders. These two have to be related to the greyskins in one way or another.",
        "",
        "Equipped with a badge signaling her purpose, this lady presumably serves as the commander of the expeditionary forces I've fought so far.",
        "The northern, eastern and western invasions were all ordered by this thing right here. The final time I have to aim my gun at any living",
        "being should be the one where I execute her on the spot.",
    });
    public static GameClasses.Entity juggernaut = new GameClasses.Entity("Juggernaut", 14, 10, 2, 3, "ascii-art/juggernaut.txt", List.of(Weapons.juggernaut), new String[] {
        "A black mask covering the face and a great, thickly woven cloak of mysterious, impenetrable fabric covering the body. These creatures whom we",
        "simply dub as the \"Geopolitans\" are at the very least two meters tall, grey skinned humanoids. They move and act as one constantly, with",
        "such precision that would be impossible to achieve using chemical signals or any other sort of communication we know of. They almost all",
        "look alike, acting more like bees of a hive than individuals. The fabric they wear is really strange, because not is it only as firm as plate",
        "armor while being as light as a silk robe, but it is the same material that was used in the construction of the dagger of the Master Witch, or",
        "the bodies of the eastern invaders. These two have to be related to the greyskins in one way or another.",
        "",
        "A cloud of tiny greyish metal particles with a blue flame burning in its center emerged from the corpse to form a humanoid body. Not something",
        "you see everyday. Though the paricles' movements may seem chaotic at first, they really follow a rigid path of movement, helping the flame",
        "maintain a coherent form. As long as the flame is alight, I believe that this creature shall remain intact.",
    });
    //endregion
    
    //region FINAL
    // public static GameClasses.Entity nemesis = new GameClasses.Entity("Nemesis", 10, 20, 2, 3, "ascii-art/nemesis.txt", List.of(Weapons.geopolitanGauntlet), new String[] {""});
}