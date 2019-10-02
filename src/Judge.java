import java.util.ArrayList;

public class Judge {
	public static Player[][] judgeAll(ArrayList<Player> players, Dealer dealer) { // Normal round end
		int size = players.size();
		for (int i = 0; i < players.size(); i ++) {
			size += players.get(i).getRecursiveSize( players.get(i));
		}
		Player[][] player = new Player[3][size];// An 2 dimentional array that contains 3 rows, each of them is for winner, tie and loser
		int dealerPoints = dealer.getPoint();
		if (dealer.isBust()) dealerPoints = 0;
		int a = 0, b = 0, c = 0;
		ArrayList<Player> temp;
		for (int i = 0; i < players.size(); i ++) {
			temp = players.get(i).recursiveAccess(players.get(i));
			for (int j = 0; j < temp.size(); j ++) {
				Player p = temp.get(j) ;
				int playerPoints = p.getPoints();
				if (playerPoints > dealerPoints && !p.isBust()) {// winner
					player[0][a] = p;
					a ++;
				}
				else if (playerPoints == dealerPoints && !p.isBust()) {// tie
					player[1][b] = p;
					b ++;
				}
				else {// loser
					player[2][c] = p;
					c ++;
				}
			}
		}
		return player;
	}

	public static boolean judgeAllBust(ArrayList<Player> players) {// Use recursive to judge split player
		ArrayList<Player> temp;
		for (int i = 0; i < players.size(); i ++) {
			temp = players.get(i).recursiveAccess(players.get(i));
			for (int j = 0; j < temp.size(); j ++) {
				Player p = temp.get(j) ;
				if (p.isBust()) continue;
				else return false;
			}
		}
		return true;
	}

	public static boolean judgeNatural(Dealer dealer, ArrayList<Player> players) {// Natural blackjack
		int size = players.size();
		for (int i = 0; i < players.size(); i ++) {
			size += players.get(i).getSplit();
		}
		Player[][] naturalWinner = new Player[2][size];
		int j = 0, a = 0, b = 0;
		for (int i = 0; i < players.size(); i ++) {
			if (players.get(i) == null) continue;
			if (players.get(i).isBlackjack()) {
				naturalWinner[0][a] = players.get(i);
				j ++;
				a ++;
			}
			else {
				naturalWinner[1][b] = players.get(i);
				b ++;
			}
		}
		if (naturalWinner[0][0] != null) {// There exists natural blackjack
			System.out.println("A natural Blackjack has occured.");
			System.out.print("The winner(s): ");
			for (int i = 0; i < naturalWinner[0].length; i ++) {
				if (naturalWinner[0][i] == null) break;
				System.out.print(naturalWinner[0][i].getName());
				naturalWinner[0][i].win(dealer);
			}
			System.out.println("");
			if (naturalWinner[1][0] != null) System.out.println("Others are lost.");
			for (int i = 0; i < naturalWinner[1].length; i ++) {
				if (naturalWinner[1][i] == null) break;
				naturalWinner[1][i].lose(dealer);
			}
			return true;
		}
		return false;
	}
}