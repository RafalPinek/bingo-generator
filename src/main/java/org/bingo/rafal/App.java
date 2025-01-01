package org.bingo.rafal;

import org.bingo.rafal.bingo90.Bingo90Generator;
import org.bingo.rafal.bingo90.BingoUtil;
import org.bingo.rafal.bingo90.Strip;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        Bingo90Generator generator = new Bingo90Generator(new BingoUtil());

        Strip strip = generator.generateStrip();

        strip.print();
    }
}
