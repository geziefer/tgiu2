package geziefer.tgiu2.view;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

import org.junit.Test;

import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Rank;
import geziefer.tgiu2.entity.Round;

public class PlayerTest {

	@Test
	public void testAllSingle() {
		Integer[] input = { 1, 2, 3, 4 };
		String[] expected = { "P1", "P2", "P3", "P4" };
		executeTest(input, expected);
	}

	@Test
	public void testDouble1st() {
		Integer[] input = { 1, 1, 3, 4 };
		String[] expected = { "P1, P2", "-", "P3", "P4" };
		executeTest(input, expected);
	}

	@Test
	public void testDouble2nd() {
		Integer[] input = { 1, 2, 2, 4 };
		String[] expected = { "P1", "P2, P3", "-", "P4" };
		executeTest(input, expected);
	}

	@Test
	public void testDouble3rd() {
		Integer[] input = { 1, 2, 3, 3 };
		String[] expected = { "P1", "P2", "P3, P4", "-" };
		executeTest(input, expected);
	}

	@Test
	public void testDoubleDouble() {
		Integer[] input = { 1, 1, 3, 3 };
		String[] expected = { "P1, P2", "-", "P3, P4", "-" };
		executeTest(input, expected);
	}

	@Test
	public void testDoubleDNF() {
		Integer[] input = { 1, 2, 3, 4, 5 };
		String[] expected = { "P1", "P2", "P3", "P4, P5" };
		executeTest(input, expected);
	}

	@Test
	public void testSeveralDNF() {
		Integer[] input = { 1, 2, 3, 4, 5, 6 };
		String[] expected = { "P1", "P2", "P3", "P4, P5, P6" };
		executeTest(input, expected);
	}

	private void executeTest(Integer[] input, String[] expected) {
		RoundsController roundsController = new RoundsController();

		ArrayList<Rank> ranks = new ArrayList<>();
		int i = 1;
		for (int number : input) {
			Rank rank = new Rank();
			rank.setRank(number);
			Player player = new Player();
			player.setName("P" + i);
			rank.setPlayer(player);
			ranks.add(rank);
			i++;
		}
		Round round = new Round();
		round.setRanks(ranks);

		String[] players = new String[4];
		for (i = 0; i < 4; i++) {
			players[i] = roundsController.getPlayersForRank(round, i + 1);
		}

		assertArrayEquals(expected, players);
	}

}
