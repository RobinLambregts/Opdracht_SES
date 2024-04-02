package be.kuleuven.candycrush.model;

import javafx.scene.paint.Color;

public sealed interface Candy permits
        ChainReactor,
        DoublePointer,
        LastRowBlower,
        NormalCandy,
        TripleMove
        {Color color();}

