## Bingo 90 ticket generator

The ticket generation is done in 2 stages:
1. Generating the positions for all numbers from 1 to 90. For this, I used a recursive backtracking algorithm.
2. Filling the generated positions with random numbers (according to assignment conditions).

The backtracking algorithm takes a strip as a parameter (which is empty at the first call). It is generating and filling positions one by one at each call until all positions are filled or the current state doesn't lead to a solution. In this case, the method will go back to the previous state and will try other positions. This recursive loop will continue until a solution is found.

## Usage

```java
StripGeneratorService stripGeneratorService = new StripGeneratorService();

Strip strip = stripGeneratorService.generateStrip();

strip.print();
```

## Example of output

```bash
Ticket 1
 6   --   --   --   --   50   62   75   87   
 9   15   24   33   --   --   68   --   --   
--   --   29   37   45   --   --   76   90   


Ticket 2
--   --   --   34   --   57   63   78   80   
--   13   20   36   42   58   --   --   --   
 4   18   21   39   --   --   --   --   82   


Ticket 3
 2   11   --   30   40   52   --   --   --   
 3   --   --   --   47   53   --   70   89   
 8   17   25   --   --   --   66   79   --   


Ticket 4
 5   --   --   --   --   56   64   72   81   
--   19   27   32   41   59   --   --   --   
--   --   --   38   46   --   67   73   86   


Ticket 5
--   --   22   --   43   51   65   --   85   
 7   16   23   35   --   54   --   --   --   
--   --   28   --   44   --   69   71   88   


Ticket 6
 1   10   --   --   --   --   60   74   83   
--   12   26   31   48   55   --   --   --   
--   14   --   --   49   --   61   77   84   
```