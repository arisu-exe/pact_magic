package io.github.fallOut015.pact_magic.world.gen.feature.structure;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.lang.reflect.Method;

public interface IStructurePieceTypePactMagic {
    IStructurePieceType ANGEL_TEMPLE_PIECE = setPieceId(AngelTemplePiece::new, "angel_temple_piece");
    IStructurePieceType DEMON_TEMPLE_PIECE = setPieceId(DemonTemplePiece::new, "demon_temple_piece");

    static IStructurePieceType setPieceId(IStructurePieceType type, String name) {
        try {
            Method iStructurePieceType$setPieceId = IStructurePieceType.class.getDeclaredMethod("setPieceId", IStructurePieceType.class, String.class);
            iStructurePieceType$setPieceId.setAccessible(true);
            return (IStructurePieceType) iStructurePieceType$setPieceId.invoke(null, type, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
