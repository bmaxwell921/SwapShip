package main.swapship.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class RandUtil {

	/**
	 * Method used to randomly choose <amount> number of elements from choices
	 * @param amount
	 * @param choices
	 * @return
	 */
	public static <E> Array<E> chooseNumFrom(int amount, Array<E> choices) {
		// Quick finish if we're gonna pick them all
		if (amount >= choices.size) {
			return choices;
		}
		
		List<Integer> choiceIndex = new ArrayList<>();
		for (int i = 0; i < choices.size; ++i) {
			choiceIndex.add(i);
		}
		
		// Basically just choose a random index, then we move that 
		// chosen index so it can't be chosen again
		int searchSpace = choices.size - 1;
		Array<E> selections = new Array<E>(amount);
		for (int i = 0; i < amount; ++i) {
			int index = choiceIndex.get(MathUtils.random(0, searchSpace));
			
			selections.add(choices.get(index));
			
			// Swap the chosen to the end
			int temp = choiceIndex.get(index);
			choiceIndex.set(index, choiceIndex.get(searchSpace));
			choiceIndex.set(searchSpace, temp);
			--searchSpace;
		}
		
		return selections;
	}
}
