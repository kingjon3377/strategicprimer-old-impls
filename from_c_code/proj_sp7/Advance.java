package proj_sp7;

import java.util.Collection;
import java.util.HashSet;

class Advance {
	class AdvanceId {
		int major; // unsigned
		int minor; // unsigned
	}
	AdvanceId number;
	/* Is it possible that the advance might be granted by judicial
	 * override despite requirements? */
	boolean overridePossible;
	/* Is judicial oversight necessary (for an intangible requirement, for
	 * instance)?  In a list of advances gained, it indicates judicial
	 * override was used and description must be modified to compensate. */
	boolean overrideDefinite;
	Collection<AdvanceId> requirements; // i.e. dependencies
	/* 
	 * Check whether all dependencies in proposed are found in existing.
	 * Returns 0 if all are found, 1 if judicial oversight is required, 2
	 * if some were not found but judicial override might change that, and
	 * 3 if some were not found and judicial override is not possible.  If
	 * there is some data error -- more requirements than there are slots,
	 * or the pointers passed to it are null, for instance -- it returns 4.
	 */
	static int checkAdvanceDep(Advance proposed, 
			Collection<Advance> existing) {
		if (proposed.overrideDefinite) {
			return 1;
		}
		Collection<AdvanceId> existingIds = new HashSet<AdvanceId>();
		for (Advance a : existing) {
			existingIds.add(a.number);
		}
		if (existingIds.containsAll(proposed.requirements)) {
			return 0;
		} else if (proposed.overridePossible) {
			return 2;
		} else {
			return 3;
		}
	}
}
