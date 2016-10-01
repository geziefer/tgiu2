package geziefer.tgiu2.view;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geziefer.tgiu2.entity.Rank;

public class RankTest {

	@Test
	public void testCheckAndAdaptRanks() {
		RoundsController roundsController = new RoundsController();
		int[] rankNumbers = { 0, 2, 1, 3 };
		List<Rank> ranks = createRankList(rankNumbers);

		List<Rank> newRanks = roundsController.checkAndAdaptRanks(ranks);
		Integer[] expectedRankNumbers = { 1, 2, 3 };
		Integer[] actualRankNumbers = createRankArray(newRanks);
		assertArrayEquals(expectedRankNumbers, actualRankNumbers);
	}

	private List<Rank> createRankList(int[] rankNumbers) {
		ArrayList<Rank> ranks = new ArrayList<>();
		for (int number : rankNumbers) {
			Rank rank = new Rank();
			rank.setRank(number);
			ranks.add(rank);
		}
		return ranks;
	}

	private Integer[] createRankArray(List<Rank> ranks) {
		Integer[] rankNumbers = new Integer[ranks.size()];
		List<Integer> rankNumberList = new ArrayList<>();
		for (Rank rank : ranks) {
			rankNumberList.add(rank.getRank());
		}
		return rankNumberList.toArray(rankNumbers);
	}

}
