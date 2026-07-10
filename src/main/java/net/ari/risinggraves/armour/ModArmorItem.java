package net.ari.risinggraves.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;

public class ModArmorItem extends ArmorItem {

    private final String setId;

    public ModArmorItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties props, String setId) {
        super(material, slot, props);
        this.setId = setId;
    }

    public String getSetId() {
        return setId;
    }
}
