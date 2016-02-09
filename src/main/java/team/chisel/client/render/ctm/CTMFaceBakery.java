package team.chisel.client.render.ctm;

import org.lwjgl.util.vector.Vector3f;
import team.chisel.client.render.QuadPos;
import team.chisel.common.Reference;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.List;

/**
 * Makes faces for CTM
 *
 * @author minecreatr
 */
public class CTMFaceBakery extends FaceBakery implements Reference {

    public static final QuadPos[] possibleQuads = new QuadPos[]{
//            new QuadPos(new Vector3f(8, 0, 0), new Vector3f(16, 16, 8)), // South West Pillar quad 1 from top
//            new QuadPos(new Vector3f(8, 0, 8), new Vector3f(16, 16, 16)), // South East Pillar quad 2 from top
//            new QuadPos(new Vector3f(0, 0, 8), new Vector3f(8, 16, 16)), // North East Pillar quad 3 from top
//            new QuadPos(new Vector3f(0, 0, 0), new Vector3f(8, 16, 8)), // North West Pillar quad 4 from top
//
//            new QuadPos(new Vector3f(0, 0, 0), new Vector3f(16, 8, 8)), // North to South Down West quad 1 from South face
//            new QuadPos(new Vector3f(0, 0, 8), new Vector3f(16, 8, 16)), // North to South Down East quad 2 from South face
//            new QuadPos(new Vector3f(0, 8, 8), new Vector3f(16, 16, 16)), // North to South Up East quad 3 from South face
//            new QuadPos(new Vector3f(0, 8, 0), new Vector3f(16, 16, 8)), // North to South Up West quad 4 from South face
//
//            new QuadPos(new Vector3f(0, 0, 0), new Vector3f(8, 8, 16)), // West to East Down North quad 1 from East face
//            new QuadPos(new Vector3f(8, 0, 0), new Vector3f(16, 8, 16)), // West to East Down South quad 2 from East face
//            new QuadPos(new Vector3f(8, 8, 0), new Vector3f(16, 16, 16)), // West to East Up South quad 3 from East face
//            new QuadPos(new Vector3f(0, 8, 0), new Vector3f(16, 16, 16)) // West to East Up North quad 4 from East face
            new QuadPos(new Vector3f(8, 0, 8), new Vector3f(16, 8, 16)), // Down South East
            new QuadPos(new Vector3f(8, 0, 0), new Vector3f(16, 8, 8)), //Down North East
            new QuadPos(new Vector3f(0, 0, 8), new Vector3f(8, 8, 16)), //Down South West
            new QuadPos(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8)), // Down North West

            new QuadPos(new Vector3f(8, 8, 8), new Vector3f(16, 16, 16)), // Up South East
            new QuadPos(new Vector3f(8, 8, 0), new Vector3f(16, 16, 8)), //Up North East
            new QuadPos(new Vector3f(0, 8, 8), new Vector3f(8, 16, 16)), //Up South West
            new QuadPos(new Vector3f(0, 8, 0), new Vector3f(8, 16, 8)), // Up North West

    };

    /**
     * Gets the id for the correct quad position
     *
     * @param f The Direction it is facing
     * @param r The Region
     *          <p/>
     *          From Up       Sides
     *          N             U
     *          W  |4|3|  E    N |4|3| S
     *          |1|2|         |1|2|
     *          S             D
     * @return The QuadPos
     */
    public static QuadPos getCorrectQuadPos(EnumFacing f, int r) {
        boolean isNorth = false;
        boolean isWest = false;
        boolean isUp = false;

        boolean isSUp = false;
        boolean isSLeft = false;
        if (r == 4) {
            isSUp = true;
            isSLeft = true;
        } else if (r == 3) {
            isSUp = true;
        } else if (r == 1) {
            isSLeft = true;
        }
        if (f == EnumFacing.UP) {
            isUp = true;
            if (isSUp) {
                isNorth = true;
            }
            if (isSLeft) {
                isWest = true;
            }
        } else if (f == EnumFacing.DOWN) {
            if (!isSUp) {
                isNorth = true;
            }
            if (isSLeft) {
                isWest = true;
            }
        } else if (f == EnumFacing.NORTH) {
            isNorth = true;
            if (isSUp) {
                isUp = true;
            }
            if (!isSLeft) {
                isWest = true;
            }
        } else if (f == EnumFacing.SOUTH) {
            if (isSUp) {
                isUp = true;
            }
            if (isSLeft) {
                isWest = true;
            }
        } else if (f == EnumFacing.WEST) {
            isWest = true;
            if (isSUp) {
                isUp = true;
            }
            if (isSLeft) {
                isNorth = true;
            }
        } else if (f == EnumFacing.EAST) {
            if (isSUp) {
                isUp = true;
            }
            if (!isSLeft) {
                isNorth = true;
            }
        }
        int num = 0;
        if (isUp) {
            num = 4;
        }
        if (isNorth) {
            num += 1;
        }
        if (isWest) {
            num += 2;
        }
        return possibleQuads[num];
    }

    /**
     * Makes a ctm face
     *
     * @param side      The Side
     * @param sprites The Sprites to use
     * @param quads     The Quad "ids"
     * @return The CTM Face
     */
    public List<BakedQuad> makeCtmFace(EnumFacing side, TextureAtlasSprite[] sprites, int[] quads) {
        //Chisel.logger.info("Making CTM face");
        return Arrays.asList(makeQuadFor(side, sprites, quads[0], 1),
                makeQuadFor(side, sprites, quads[1], 2),
                makeQuadFor(side, sprites, quads[2], 3),
                makeQuadFor(side, sprites, quads[3], 4)
        );
    }

    private BakedQuad makeQuadFor(EnumFacing side, TextureAtlasSprite[] sprites, int quad, int quadSection) {
        TextureAtlasSprite s = sprites[1];
//        if (resources.type == RenderType.CTMH || resources.type == RenderType.CTMV) {
//            if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
//                if (side == EnumFacing.UP) {
//                    s = resources.top;
//                } else {
//                    s = resources.bottom;
//                }
//                return ModelNonCTM.makeQuad(side, s, resources.type, true);
//            }
//        }
////        else if (resources.type == RenderType.CTMV){
////            if (side==EnumFacing.NORTH||side==EnumFacing.SOUTH||side==EnumFacing.WEST||side==EnumFacing.EAST){
////                s=resources.side;
////                return ModelNonCTM.makeQuad(side, s, resources.type);
////            }
////        }
//        if (CTM.isDefaultTexture(quad)) {
//            if (!(resources.type == RenderType.CTMH || resources.type == RenderType.CTMV)) {
//                s = resources.texture;
//            }
////            Chisel.logger.info("Using resources.texture for "+quad+" and "+resources.getParent().getName()+" variant "+resources.getVariantName());
//        } else {
//            s = resources.ctmTexture;
//        }
//        if (resources.type == RenderType.CTMH || resources.type == RenderType.CTMV) {
////            int old = quad;
////            quad = CTM.remapCTM(quad);
//            //Chisel.logger.info("Remapping ctm(h/v) "+old+" to "+quad);
//        }
        if (CTM.isDefaultTexture(quad)){
            s = sprites[0];
        }
        QuadPos pos = getCorrectQuadPos(side, quadSection);
        return makeRealQuad(pos, side, s, quad);


    }

    private BakedQuad makeRealQuad(QuadPos pos, EnumFacing side, TextureAtlasSprite s, int quad) {
        return makeBakedQuad(pos.from, pos.to, new BlockPartFace(side, -1, s.getIconName(), new BlockFaceUV(CTM.uvs[quad].toArray(), 0)),
                s, side, ModelRotation.X0_Y0, new BlockPartRotation(new Vector3f(1, 0, 0), side.getAxis(), 0, false), false, true);
    }
}
