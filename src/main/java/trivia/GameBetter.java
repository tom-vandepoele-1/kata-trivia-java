package trivia;

import java.util.ArrayList;
import java.util.List;

// REFACTOR ME
public class GameBetter implements IGame {
   final List<String> players = new ArrayList<>();
   final int[] places = new int[6];
   final int[] purses = new int[6];
   final boolean[] inPenaltyBox = new boolean[6];

   final List<String> popQuestions = new ArrayList<>();
   final List<String> scienceQuestions = new ArrayList<>();
   final List<String> sportsQuestions = new ArrayList<>();
   final List<String> rockQuestions = new ArrayList<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (int i = 0; i < 50; i++) {
         popQuestions.add("Pop Question " + i);
         scienceQuestions.add(("Science Question " + i));
         sportsQuestions.add(("Sports Question " + i));
         rockQuestions.add(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean add(String playerName) {
      players.add(playerName);
      places[howManyPlayers()] = 0;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void askQuestion() {
      if ("Pop".equals(currentCategory()))
         System.out.println(popQuestions.remove(0));
      if ("Science".equals(currentCategory()))
         System.out.println(scienceQuestions.remove(0));
      if ("Sports".equals(currentCategory()))
         System.out.println(sportsQuestions.remove(0));
      if ("Rock".equals(currentCategory()))
         System.out.println(rockQuestions.remove(0));
   }


   private String currentCategory() {
      int currentPlayerPlace = places[currentPlayer];
      return provideCategory(currentPlayerPlace);
   }

   private String provideCategory(int currentPlayerPlace) {
      switch (currentPlayerPlace) {
         case 0:
         case 4:
         case 8:
            return "Pop";
         case 1:
         case 5:
         case 9:
            return "Science";
         case 2:
         case 6:
         case 10:
            return "Sports";
         default:
            return "Rock";
      }
   }

   public boolean wasCorrectlyAnswered() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) {
               currentPlayer = 0;
            }

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) {
               currentPlayer = 0;
            }
            return true;
         }


      }
      System.out.println("Answer was corrent!!!!");
      purses[currentPlayer]++;
      System.out.println(players.get(currentPlayer)
          + " now has "
          + purses[currentPlayer]
          + " Gold Coins.");

      boolean winner = didPlayerWin();
      currentPlayer++;
      if (currentPlayer == players.size()) {
         currentPlayer = 0;
      }

      return winner;

   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
