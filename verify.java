import java.util.*;
import java.io.*;

public class verify
{
  public static class Room
  {
    public String name;
    public int rating;
    public int min;

    public Room(String name, int rating, int min)
    {
      this.name = name;
      this.rating = rating;
      this.min = min;
    }

    public String getName()
    {
      return name;
    }
    public int getRating()
    {
      return rating;
    }
    public int getMin()
    {
      return min;
    }
  }  

  public static class Bid
  {
    public String name;
    // a + bq
    public int type;
    public int a;
    public int b;
    public int singleChoice = -1;

    public Bid(String name, int type, int a, int b)
    {
      this.name = name;
      this.type = type;
      this.a = a;
      this.b = b;
    }

    public Bid(String name, int type, int a, int b, int singleChoice)
    {
      this.name = name;
      this.type = type;
      this.a = a;
      this.b = b;
      this.singleChoice = singleChoice;
    }

    public String getName()
    {
      return name;
    }
    public int getType()
    {
      return type;
    }
    public int getA()
    {
      return a;
    }
    public int getB()
    {
      return b;
    }
    public int getSingleChoice()
    {
      return singleChoice;
    }
  }

	public static void main(String args[])
	{
    ArrayList<Room> rooms = new ArrayList<Room>();
    ArrayList<Bid> bids = new ArrayList<Bid>();
		try 
    {
      File input = new File(args[0]);
      File result = new File(args[1]);
      File correct = new File(args[2]);
      Scanner sc = new Scanner(input);
      Scanner scResult = new Scanner(result);
      Scanner scCorrect = new Scanner(correct);

      int numRooms = Integer.parseInt(sc.nextLine());
      int bidCounter = 0;


      //Iterates through the rooms
      for(int i = 0; i < numRooms; i ++)
      {
        String[] roomInfo = sc.nextLine().split(" ");

        //Stores rooms
        rooms.add(new Room(String.valueOf(i), Integer.parseInt(roomInfo[0]), Integer.parseInt(roomInfo[1])));
        
        //Create initial fake bids
        bids.add(new Bid(String.valueOf(-1), 1, Integer.parseInt(roomInfo[1]), 0, i));
      }
      while(sc.hasNextLine())
      {
        String[] bidInfo = sc.nextLine().split(" ");
        if(bidInfo[0].equals("1"))
        {
          //single bid
          int offer = Integer.parseInt(bidInfo[1]);
          int choice = Integer.parseInt(bidInfo[2]);

          bids.add(new Bid(String.valueOf(bidCounter), 1, offer, 0, choice));
          bidCounter++;
        }      
        else if(bidInfo[0].equals("2"))
        {
          //linear bid
          int constant = Integer.parseInt(bidInfo[1]);
          int multiplier = Integer.parseInt(bidInfo[2]);

          bids.add(new Bid(String.valueOf(bidCounter), 2, constant, multiplier));
          bidCounter++;
        }
        else
        {
          //skip
        }
      }
      sc.close();

      // for(Bid b : bids)
      // {
      //   int bidName = Integer.parseInt(b.getName());
      //   System.out.println("bidName " + bidName + " type " + b.getType() + " a " + b.getA() + " b " + b.getB() + " singlechoice " + b.getSingleChoice());
      // }

      //Take in result & correct

      int checkResultSum = 0;
      int maxResultWeight = -1;
      int checkCorrectSum = 0;
      int maxCorrectWeight = -1;
      boolean correctness = true;
      
      int everyOther = 100;
      int counter = 0;
      while(scResult.hasNextLine())
      {
        checkResultSum = 0;
        checkCorrectSum = 0;
        if(correctness == false)
        {
          System.out.println("Incorrect");
          break;
        }
        String[] weightCombo = scResult.nextLine().split(" ");
        String[] correctWeightCombo = scCorrect.nextLine().split(" ");

        maxResultWeight = Integer.parseInt(weightCombo[0]);
        maxCorrectWeight = Integer.parseInt(correctWeightCombo[0]);
        if(counter % everyOther == 0)
        {
          for(int i = 1; i < weightCombo.length; i ++)
          {
            //i-1 is the room name
            //weightCombo[i] is the bidder name
            for(Bid b : bids)
            {
              //found the bidder's information
              if(b.getName().equals(weightCombo[i]))
              {
                //Single bid
                if(b.getType() == 1 && b.getSingleChoice() != (i-1))
                {
                  //do nothing
                }
                else
                {
                  for(Room r : rooms)
                  {
                    if((i-1) == Integer.parseInt(r.getName()))
                    {
                      if(b.getType() == 1)
                      {
                        if(b.getSingleChoice() != (i-1))
                        {
                          //System.out.println("We have a problem");
                          System.out.println(b.getSingleChoice());
                        }
                      }
                      checkResultSum = checkResultSum + b.getA() + b.getB() * r.getRating();  
                    }
                  }
                }
              }



              if(b.getName().equals(correctWeightCombo[i]))
              {
                //Single bid
                if(b.getType() == 1 && b.getSingleChoice() != (i-1))
                {
                  //do nothing
                }
                else
                {
                  for(Room r : rooms)
                  {
                    if((i-1) == Integer.parseInt(r.getName()))
                    {
                      if(b.getType() == 1)
                      {
                        if(b.getSingleChoice() != (i-1))
                        {
                          //System.out.println("We have a problem");
                          System.out.println(b.getSingleChoice());
                        }
                      }
                      checkCorrectSum = checkCorrectSum + b.getA() + b.getB() * r.getRating();  
                    }
                  }
                }
              }
            }
          }
        }
        
        //System.out.println(maxResultWeight);
        //System.out.println(checkCorrectSum);
        
        if(counter % everyOther == 0)
        {
          correctness = ((maxResultWeight == checkResultSum) && (maxCorrectWeight == checkCorrectSum));
          if(correctness == false)
          {
            System.out.println("\nyour output ");
            System.out.println("weight " + maxResultWeight);
            System.out.println("calculated weight " + checkResultSum);
            System.out.println("\ncorrect output ");
            System.out.println("correct weight " + maxCorrectWeight);
            System.out.println("correct weight " + checkCorrectSum);
            System.out.println();
          }
        }
        else
        {
          correctness = (maxResultWeight == maxCorrectWeight);
          if(correctness == false)
          {
            System.out.println("\nyour output ");
            System.out.println("weight " + maxResultWeight);
            System.out.println("\ncorrect output ");
            System.out.println("correct weight " + maxCorrectWeight);
            System.out.println();
          }
        }

        counter ++;
      }
      if(correctness == true)
      {
        System.out.println("correct");
      }
      scCorrect.close();
      scResult.close();
    } 
    catch (IOException ioe) {
      System.out.println("Trouble reading from the file: " + ioe.getMessage());
    } 
    catch (Exception e)
    {
      System.out.println(e);
    }
	}
} 
