package geziefer.tgiu2.view;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geziefer.tgiu2.entity.Rank;

public class RankTest {

	@Test
	public void testSkipNAs() {
		Integer[] input = { 0, 0, 1, 2, 3 };
		Integer[] expected = { 1, 2, 3 };
		executeTest(input, expected);
	}

	@Test
	public void testOrdered() {
		Integer[] input = { 3, 2, 1, 4, 6, 5 };
		Integer[] expected = { 1, 2, 3, 4, 5, 6 };
		executeTest(input, expected);
	}

	@Test
	public void testCorrect() {
		Integer[] input = { 1, 2, 3, 4, 5, 6 };
		Integer[] expected = { 1, 2, 3, 4, 5, 6 };
		executeTest(input, expected);
	}

	@Test
	public void testSameCorrect() {
		Integer[] input = { 1, 2, 2, 4 };
		Integer[] expected = { 1, 2, 2, 4 };
		executeTest(input, expected);
	}

	@Test
	public void testSameFirst() {
		Integer[] input = { 1, 1, 2, 3 };
		Integer[] expected = { 1, 1, 3, 4 };
		executeTest(input, expected);
	}

	@Test
	public void testSameMiddle() {
		Integer[] input = { 1, 2, 2, 3 };
		Integer[] expected = { 1, 2, 2, 4 };
		executeTest(input, expected);
	}

	@Test
	public void testSameLast() {
		Integer[] input = { 1, 2, 3, 3 };
		Integer[] expected = { 1, 2, 3, 3 };
		executeTest(input, expected);
	}

	@Test
	public void testSameSeveralCorrect() {
		Integer[] input = { 1, 2, 2, 2, 5 };
		Integer[] expected = { 1, 2, 2, 2, 5 };
		executeTest(input, expected);
	}

	@Test
	public void testSameSeveralFirst() {
		Integer[] input = { 1, 1, 1, 2 };
		Integer[] expected = { 1, 1, 1, 4 };
		executeTest(input, expected);
	}

	@Test
	public void testSameSeveralMiddle() {
		Integer[] input = { 1, 2, 2, 2, 3 };
		Integer[] expected = { 1, 2, 2, 2, 5 };
		executeTest(input, expected);
	}

	@Test
	public void testSameSeveralLast() {
		Integer[] input = { 1, 2, 2, 2 };
		Integer[] expected = { 1, 2, 2, 2 };
		executeTest(input, expected);
	}

	@Test
	public void testSameDoubleTogether() {
		Integer[] input = { 1, 2, 2, 3, 3, 4 };
		Integer[] expected = { 1, 2, 2, 4, 4, 6 };
		executeTest(input, expected);
	}

	@Test
	public void testSameDoubleApart() {
		Integer[] input = { 1, 1, 2, 3, 3 };
		Integer[] expected = { 1, 1, 3, 4, 4 };
		executeTest(input, expected);
	}

	@Test
	public void testSeveralDouble() {
		Integer[] input = { 1, 1, 1, 2, 2, 2 };
		Integer[] expected = { 1, 1, 1, 4, 4, 4 };
		executeTest(input, expected);
	}

	@Test
	public void testSeveralDoubleCorrect() {
		Integer[] input = { 1, 1, 1, 4, 4, 4 };
		Integer[] expected = { 1, 1, 1, 4, 4, 4 };
		executeTest(input, expected);
	}

	@Test
	public void testGapFirst() {
		Integer[] input = { 2, 3, 4 };
		Integer[] expected = { 1, 2, 3 };
		executeTest(input, expected);
	}

	@Test
	public void testGapMiddle() {
		Integer[] input = { 1, 3, 4 };
		Integer[] expected = { 1, 2, 3 };
		executeTest(input, expected);
	}

	@Test
	public void testGapLast() {
		Integer[] input = { 1, 2, 4 };
		Integer[] expected = { 1, 2, 3 };
		executeTest(input, expected);
	}

	@Test
	public void testGapDouble() {
		Integer[] input = { 1, 3, 5 };
		Integer[] expected = { 1, 2, 3 };
		executeTest(input, expected);
	}

	private void executeTest(Integer[] input, Integer[] expected) {
		RoundsController roundsController = new RoundsController();
		ArrayList<Rank> ranks = new ArrayList<>();
		for (int number : input) {
			Rank rank = new Rank();
			rank.setRank(number);
			ranks.add(rank);
		}

		List<Rank> newRanks = roundsController.checkAndAdaptRanks(ranks);

		Integer[] rankNumbers = new Integer[newRanks.size()];
		List<Integer> rankNumberList = new ArrayList<>();
		for (Rank rank : newRanks) {
			rankNumberList.add(rank.getRank());
		}
		rankNumbers = rankNumberList.toArray(rankNumbers);

		assertArrayEquals(expected, rankNumbers);
	}

}
