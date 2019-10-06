import java.util.ArrayList;

public class Judge {
	public static Player[][] judgeAll(ArrayList<Player> players, Player dealer) { // Normal round end
		int size = players.size();
		Player[][] player = new Player[3][size];// An 2 dimentional array that contains 3 rows, each of them is for winner, tie and loser
		int dealerPoints = dealer.getPoints();
		if (dealer.isBust()) dealerPoints = 0;
		int a = 0, b = 0, c = 0;
		for (int i = 0; i < size; i ++) {
			Player p = players.get(i);
			if(p.isFold())continue;
			if(p==dealer) {
				continue;
			}
			int playerPoints = p.getPoints();
			if ( !p.isBust() && ( playerPoints > dealerPoints || ( p.isThirtyone() && !dealer.isThirtyone()))) {// winner
				player[0][a] = p;
				a ++;
			}
			else if (false) {// tie
				player[1][b] = p;
				b ++;
			}
			else {// loser
				player[2][c] = p;
				c ++;
			}
		}
		return player;
	}

	public static boolean judgeAllBust(ArrayList<Player> players) {// Use recursive to judge split player
		ArrayList<Player> temp;
		for (int i = 0; i < players.size(); i ++) {
			Player p = players.get(i) ;
			if (p.isBust()) continue;
			else return false;
		}
		return true;
	}
}