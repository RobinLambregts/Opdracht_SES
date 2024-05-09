package be.kuleuven.candycrush.model;

import javafx.scene.paint.Color;

public sealed interface Candy permits ChainReactor, DoublePointer, EmptyCandy, LastRowBlower, NormalCandy, TripleMove
        {Color color();}

