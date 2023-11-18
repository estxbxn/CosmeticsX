package fr.zebulon.cosmeticsx.cosmetics.factory.properties;

import fr.zebulon.cosmeticsx.cosmetics.CosmeticSlot;

import java.util.Optional;

public interface ISlotHandler {
	/**
	 * Get the type of the slot where the cosmetic is
	 *
	 * @return the slot
	 */
	Optional<CosmeticSlot> getSlot();

	/**
	 * Set the cosmetic on a cosmetic slot using {@link CosmeticSlot}
	 *
	 * @param slot the slot
	 */
	void setSlot(CosmeticSlot slot);
}
