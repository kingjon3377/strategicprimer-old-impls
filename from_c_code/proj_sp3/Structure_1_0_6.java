package proj_sp3;
abstract class Structure_1_0_6 extends Common_1_0_6 {
	Structure_1_0_6() { super(); }
	Structure_1_0_6(final Structure_1_0_6 rhs) { super(rhs); }
	Structure_1_0_6 copy(final Structure_1_0_6 rhs) { 
		super.copy(rhs);
		return this;
	}
	abstract boolean action();
}
