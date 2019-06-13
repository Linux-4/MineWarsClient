package net.minecraft.util.datafix.walkers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.IDataFixer;

public class ItemStackDataLists extends Filtered {
	private final String[] matchingTags;

	public ItemStackDataLists(Class<?> p_i47310_1_, String... p_i47310_2_) {
		super(p_i47310_1_);
		this.matchingTags = p_i47310_2_;
	}

	NBTTagCompound filteredProcess(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
		for (String s : this.matchingTags) {
			compound = DataFixesManager.processInventory(fixer, compound, versionIn, s);
		}

		return compound;
	}
}
