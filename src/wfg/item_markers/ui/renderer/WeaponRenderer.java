package wfg.item_markers.ui.renderer;

// import java.awt.Color;

// import org.lwjgl.opengl.GL11;
// import org.lwjgl.util.vector.ReadableVector2f;
// import org.lwjgl.util.vector.Vector2f;

// import com.fs.graphics.Sprite;
// import com.fs.starfarer.api.combat.WeaponAPI;
// import com.fs.starfarer.api.loading.MissileSpecAPI;
// import com.fs.starfarer.api.loading.WeaponSlotAPI;
// import com.fs.starfarer.api.loading.WeaponSpecAPI;

// TODO maybe use in the future
public class WeaponRenderer {
    // private Sprite hardPointSprite;
    // private Sprite hardPointUnderSprite;
    // private Sprite \u00d5\u00d2\u00d8o00;
    // private Sprite gunSprite;
    // private Color spriteColor;
    // private WeaponSpecAPI weaponSpec;
    // private MissileSpecAPI missileSpec;
    // private WeaponSlotAPI weaponSlot;
    // private boolean hasHint1 = false;
    // private float \u00f4\u00d2\u00d8o00 = 1.0f;
    // private boolean \u00f4\u00d3\u00d8o00 = false;
    // private float \u00d8\u00d3\u00d8o00 = -1.0f;
    // private float return.int$private = 1.0f;
    // private float for.int$private = 1.0f;

    // public WeaponRenderer(WeaponSpecAPI baseWeaponSpec, WeaponSlotAPI y, Color color, boolean bl) {
    //     this(baseWeaponSpec, y, color, bl, false);
    // }

    // public WeaponRenderer(WeaponSpecAPI baseWeaponSpec, WeaponSlotAPI y, Color color, boolean forceHardpointGunSprite, boolean isHighlight) {
    //     this.weaponSpec = baseWeaponSpec;
    //     if (y == null) {
    //         y = new WeaponSlotAPI("blah", baseWeaponSpec.getType(), baseWeaponSpec.getSize(), WeaponSlotAPI.o.class, new Vector2f(), new W("blah", new Vector2f()), 0.0f, 90.0f);
    //     }
    //     this.weaponSlot = y;
    //     if (baseWeaponSpec instanceof N || baseWeaponSpec instanceof if) {
    //         N n2 = (N)baseWeaponSpec;
    //         this.hasHint1 = n2.hasHint(WeaponSpecAPI.o.return);
    //         if (n2.hasHint(WeaponSpecAPI.o.\u00d800000) || n2.hasHint(WeaponSpecAPI.o.\u00d400000)) {
    //             this.missileSpec = (o00o)n2.getProjectileSpec();
    //             Q q2 = this.missileSpec.getHullSpec().getSpriteSpec();
    //             this.\u00d5\u00d2\u00d8o00 = new Sprite(q2.Object());
    //             if (forceHardpointGunSprite) {
    //                 this.\u00d5\u00d2\u00d8o00.setCenter(this.missileSpec.getHullSpec().getSpriteSpec().\u00d500000(), this.missileSpec.getHullSpec().getSpriteSpec().\u00d200000());
    //                 this.\u00d5\u00d2\u00d8o00.setSize(this.missileSpec.getHullSpec().getSpriteSpec().\u00f400000(), this.missileSpec.getHullSpec().getSpriteSpec().o00000());
    //             }
    //         }
    //         if (forceHardpointGunSprite) {
    //             if (y.isHardpoint()) {
    //                 this.hardPointSprite = new Sprite(baseWeaponSpec.getHardpointSpriteName());
    //                 this.hardPointUnderSprite = new Sprite(baseWeaponSpec.getHardpointUnderSpriteName());
    //             } else {
    //                 this.hardPointSprite = new Sprite(baseWeaponSpec.getTurretSpriteName());
    //                 this.hardPointUnderSprite = new Sprite(baseWeaponSpec.getTurretUnderSpriteName());
    //             }
    //         } else {
    //             this.hardPointSprite = new Sprite(baseWeaponSpec.getHardpointSpriteName());
    //         }
    //         if (n2.getHardpointGunSpriteName() != null) {
    //             this.gunSprite = forceHardpointGunSprite ? (y.isHardpoint() ? new Sprite(n2.getHardpointGunSpriteName()) : new Sprite(n2.getTurretGunSpriteName())) : new Sprite(n2.getHardpointGunSpriteName());
    //         }
    //     } else if (baseWeaponSpec instanceof com.fs.starfarer.loading.specs.Object) {
    //         com.fs.starfarer.loading.specs.Object object = (com.fs.starfarer.loading.specs.Object)baseWeaponSpec;
    //         if (forceHardpointGunSprite) {
    //             if (y.isHardpoint()) {
    //                 this.hardPointSprite = new Sprite(baseWeaponSpec.getHardpointSpriteName());
    //                 this.hardPointUnderSprite = new Sprite(baseWeaponSpec.getHardpointUnderSpriteName());
    //             } else {
    //                 this.hardPointSprite = new Sprite(baseWeaponSpec.getTurretSpriteName());
    //                 this.hardPointUnderSprite = new Sprite(baseWeaponSpec.getTurretUnderSpriteName());
    //             }
    //         } else {
    //             this.hardPointSprite = new Sprite(object.getHardpointSpriteName());
    //         }
    //     }
    //     if (y.isHardpoint() && !isHighlight) {
    //         if (this.hardPointSprite != null) {
    //             this.hardPointSprite.setCenter(this.hardPointSprite.getWidth() / 2.0f, this.hardPointSprite.getHeight() * 0.25f);
    //         }
    //         if (this.hardPointUnderSprite != null) {
    //             this.hardPointUnderSprite.setCenter(this.hardPointUnderSprite.getWidth() / 2.0f, this.hardPointUnderSprite.getHeight() * 0.25f);
    //         }
    //         if (this.gunSprite != null) {
    //             this.gunSprite.setCenter(this.gunSprite.getWidth() / 2.0f, this.gunSprite.getHeight() * 0.25f);
    //         }
    //     }
    //     this.\u00d300000(color);
    // }

    // public void \u00d300000(Color color) {
    //     this.spriteColor = color;
    //     this.hardPointSprite.setColor(color);
    //     if (this.gunSprite != null) {
    //         this.gunSprite.setColor(color);
    //     }
    //     if (this.hardPointUnderSprite != null) {
    //         this.hardPointUnderSprite.setColor(color);
    //     }
    //     if (this.\u00d5\u00d2\u00d8o00 != null) {
    //         this.\u00d5\u00d2\u00d8o00.setColor(color);
    //     }
    // }

    // public void o00000(int n2, int n3) {
    //     this.hardPointSprite.setBlendFunc(n2, n3);
    //     if (this.gunSprite != null) {
    //         this.gunSprite.setBlendFunc(n2, n3);
    //     }
    //     if (this.hardPointUnderSprite != null) {
    //         this.hardPointUnderSprite.setBlendFunc(n2, n3);
    //     }
    //     if (this.\u00d5\u00d2\u00d8o00 != null) {
    //         this.\u00d5\u00d2\u00d8o00.setBlendFunc(n2, n3);
    //     }
    // }

    // public void \u00d800000(float f2, float f3, float f4) {
    //     if (this.gunSprite != null) {
    //         this.gunSprite.setAlphaMult(f4);
    //         this.gunSprite.renderAtCenter(f2, f3);
    //     }
    //     if (this.\u00d5\u00d2\u00d8o00 != null) {
    //         this.\u00d5\u00d2\u00d8o00.setAlphaMult(f4);
    //         this.\u00d5\u00d2\u00d8o00.renderAtCenter(f2, f3);
    //     } else {
    //         this.hardPointSprite.setAlphaMult(f4);
    //         this.hardPointSprite.renderAtCenter(f2, f3);
    //     }
    // }

    // public void \u00f5o0000(float f2) {
    //     this.\u00f4\u00d2\u00d8o00 = f2;
    // }

    // public void class.private() {
    //     this.o00000(770, 771);
    // }

    // public void \u00d8\u00f6O000() {
    //     this.o00000(770, 1);
    // }

    // public void \u00d300000(float f2, float f3, float f4, float f5) {
    //     GL11.glPushMatrix();
    //     GL11.glTranslatef((float)f2, (float)f3, (float)0.0f);
    //     GL11.glScalef((float)this.\u00f4\u00d2\u00d8o00, (float)this.\u00f4\u00d2\u00d8o00, (float)1.0f);
    //     if (this.hardPointUnderSprite != null) {
    //         this.hardPointUnderSprite.setAngle(f4);
    //         this.hardPointUnderSprite.setAlphaMult(f5);
    //         this.hardPointUnderSprite.renderAtCenter(0.0f, 0.0f);
    //     }
    //     if (this.gunSprite != null && this.hasHint1) {
    //         this.gunSprite.setAngle(f4);
    //         this.gunSprite.setAlphaMult(f5);
    //         this.gunSprite.renderAtCenter(0.0f, 0.0f);
    //     }
    //     this.hardPointSprite.setAngle(f4);
    //     this.hardPointSprite.setAlphaMult(f5);
    //     this.hardPointSprite.renderAtCenter(0.0f, 0.0f);
    //     if (this.gunSprite != null && !this.hasHint1) {
    //         this.gunSprite.setAngle(f4);
    //         this.gunSprite.setAlphaMult(f5);
    //         this.gunSprite.renderAtCenter(0.0f, 0.0f);
    //     }
    //     if (this.\u00d5\u00d2\u00d8o00 != null) {
    //         this.\u00d5\u00d2\u00d8o00.setAlphaMult(f5);
    //         int n2 = ooOO.o00000(this.weaponSlot, this.weaponSpec);
    //         int n3 = 0;
    //         while (n3 < n2) {
    //             Vector2f vector2f = this.new(ooOO.new(this.weaponSlot, this.weaponSpec, n3), f4);
    //             if (this.\u00d8\u00d3\u00d8o00 > 0.0f) {
    //                 vector2f.scale(this.\u00d8\u00d3\u00d8o00);
    //             }
    //             float f6 = f4 + ooOO.\u00d300000(this.weaponSlot, this.weaponSpec, n3);
    //             this.\u00d5\u00d2\u00d8o00.setAngle(f6);
    //             this.\u00d5\u00d2\u00d8o00.renderAtCenter(0.0f + vector2f.x, 0.0f + vector2f.y);
    //             ++n3;
    //         }
    //     }
    //     GL11.glPopMatrix();
    // }

    // public float \u00f6\u00f6O000() {
    //     return this.hardPointSprite.getWidth();
    // }

    // private Vector2f new(Vector2f vector2f, float f2) {
    //     Vector2f vector2f2 = new Vector2f();
    //     float f3 = f2 + 90.0f;
    //     float f4 = (float)Math.cos(Math.toRadians(f3));
    //     float f5 = (float)Math.sin(Math.toRadians(f3));
    //     Vector2f vector2f3 = new Vector2f((ReadableVector2f)vector2f2);
    //     vector2f3.x += vector2f.x * f4 - vector2f.y * f5;
    //     vector2f3.y += vector2f.x * f5 + vector2f.y * f4;
    //     return vector2f3;
    // }

    // @Override
    // public void new(Position Position2, float f2) {
    //     float f3 = Position2.getCenterY();
    //     this.\u00d800000(Position2.getCenterX(), f3, f2);
    // }

    // public boolean \u00d6\u00f6O000() {
    //     return this.\u00f4\u00d3\u00d8o00;
    // }

    // public void \u00f5O0000(boolean bl) {
    //     this.\u00f4\u00d3\u00d8o00 = bl;
    // }

    // public void \u00d800000(float f2, float f3) {
    //     if (this.\u00d8\u00d3\u00d8o00 >= 0.0f) {
    //         return;
    //     }
    //     float f4 = this.hardPointSprite.getWidth();
    //     float f5 = this.hardPointSprite.getHeight();
    //     Utils.o00000(this.hardPointSprite, f2, f3);
    //     float f6 = this.hardPointSprite.getWidth() / f4;
    //     if (this.\u00f4\u00d3\u00d8o00) {
    //         if (this.weaponSpec.getSize() == WeaponAPI.WeaponSize.LARGE) {
    //             f6 = 0.5f;
    //         } else if (this.weaponSpec.getSize() == WeaponAPI.WeaponSize.MEDIUM) {
    //             f6 = 0.75f;
    //         } else if (this.weaponSpec.getSize() == WeaponAPI.WeaponSize.SMALL) {
    //             f6 = 1.0f;
    //         }
    //         if (f5 * f6 > f3) {
    //             f6 = f3 / Math.max(1.0f, f5);
    //         }
    //     }
    //     float f7 = 0.0f;
    //     float f8 = 0.0f;
    //     if (this.\u00d5\u00d2\u00d8o00 != null) {
    //         f7 = this.\u00d5\u00d2\u00d8o00.getWidth();
    //         f8 = this.\u00d5\u00d2\u00d8o00.getHeight();
    //         if (!this.\u00f4\u00d3\u00d8o00) {
    //             float f9 = 0.0f;
    //             for (Vector2f vector2f : this.weaponSpec.getTurretFireOffsets()) {
    //                 float f10 = vector2f.x;
    //                 if (!(f10 > f9)) continue;
    //                 f9 = f10;
    //             }
    //             if (f9 > 0.0f) {
    //                 Utils.o00000(this.\u00d5\u00d2\u00d8o00, f2, f3 - f9 * 0.75f);
    //                 float f11 = this.\u00d5\u00d2\u00d8o00.getWidth() / f7;
    //                 if (f11 < f6) {
    //                     f6 = f11;
    //                 }
    //             }
    //         }
    //     }
    //     this.\u00d8\u00d3\u00d8o00 = f6;
    //     this.hardPointSprite.setSize(f4 * f6, f5 * f6);
    //     if (this.gunSprite != null) {
    //         this.gunSprite.setSize(this.gunSprite.getWidth() * f6, this.gunSprite.getHeight() * f6);
    //     }
    //     if (this.hardPointUnderSprite != null) {
    //         this.hardPointUnderSprite.setSize(this.hardPointUnderSprite.getWidth() * f6, this.hardPointUnderSprite.getHeight() * f6);
    //     }
    //     if (this.\u00d5\u00d2\u00d8o00 != null) {
    //         this.\u00d5\u00d2\u00d8o00.setSize(f7 * f6, f8 * f6);
    //         this.\u00d5\u00d2\u00d8o00.setCenter(this.\u00d5\u00d2\u00d8o00.getCenterX() * f6, this.\u00d5\u00d2\u00d8o00.getCenterY() * f6);
    //     }
    // }

    // public WeaponSpecAPI \u00f5\u00f6O000() {
    //     return this.weaponSpec;
    // }

    // public void o00000(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, boolean bl) {
    //     float f12 = (f2 + f4 + f6 + f8) / 4.0f;
    //     float f13 = (f3 + f5 + f7 + f9) / 4.0f;
    //     float f14 = (f8 - f2 + f6 - f4) / 2.0f;
    //     float f15 = (f5 - f3 + f7 - f9) / 2.0f;
    //     f14 += 10.0f;
    //     f15 += 10.0f;
    //     if (this.hardPointSprite != null) {
    //         float f16;
    //         float f17 = this.hardPointSprite.getWidth();
    //         if (f17 < 50.0f) {
    //             f17 = 50.0f;
    //         }
    //         if ((f16 = f17 / 70.0f) < 0.5f) {
    //             f16 = 0.5f;
    //         }
    //         if (f16 > 1.0f) {
    //             f16 = 1.0f;
    //         }
    //         this.\u00f4\u00d2\u00d8o00 = f16;
    //     }
    //     Vector2f vector2f = Vector2f.sub((Vector2f)new Vector2f((f8 + f6) / 2.0f, (f9 + f7) / 2.0f), (Vector2f)new Vector2f(f12, f13), (Vector2f)new Vector2f());
    //     Vector2f vector2f2 = Vector2f.sub((Vector2f)new Vector2f((f4 + f6) / 2.0f, (f5 + f7) / 2.0f), (Vector2f)new Vector2f(f12, f13), (Vector2f)new Vector2f());
    //     vector2f.normalise();
    //     vector2f2.normalise();
    //     GL11.glPushMatrix();
    //     if (this.hardPointUnderSprite != null) {
    //         this.hardPointUnderSprite.setAlphaMult(f10);
    //         this.o00000(f2, f3, f4, f5, f6, f7, f8, f9, this.hardPointUnderSprite, f10, f11, false);
    //     }
    //     if (this.gunSprite != null && this.hasHint1) {
    //         this.gunSprite.setAlphaMult(f10);
    //         this.o00000(f2, f3, f4, f5, f6, f7, f8, f9, this.gunSprite, f10, f11, bl);
    //     }
    //     this.hardPointSprite.setAlphaMult(f10);
    //     this.o00000(f2, f3, f4, f5, f6, f7, f8, f9, this.hardPointSprite, f10, f11, bl);
    //     if (this.gunSprite != null && !this.hasHint1) {
    //         this.gunSprite.setAlphaMult(f10);
    //         this.o00000(f2, f3, f4, f5, f6, f7, f8, f9, this.gunSprite, f10, f11, bl);
    //     }
    //     if (this.\u00d5\u00d2\u00d8o00 != null) {
    //         float f18 = Utils.\u00d300000(vector2f2) - 90.0f;
    //         float f19 = this.\u00d5\u00d2\u00d8o00.getWidth();
    //         float f20 = this.\u00d5\u00d2\u00d8o00.getHeight();
    //         float f21 = f14 * this.return.int$private / this.hardPointSprite.getWidth();
    //         if (f21 > 1.0f) {
    //             f21 = 1.0f;
    //         }
    //         this.\u00d5\u00d2\u00d8o00.setSize(f19 * f21, f20 * f21);
    //         int n2 = ooOO.o00000(this.weaponSlot, this.weaponSpec);
    //         int n3 = 0;
    //         while (n3 < n2) {
    //             Vector2f vector2f3 = this.new(ooOO.new(this.weaponSlot, this.weaponSpec, n3), 0.0f);
    //             vector2f3.scale(f21);
    //             vector2f3.scale(this.\u00f4\u00d2\u00d8o00);
    //             float f22 = f18 + ooOO.\u00d300000(this.weaponSlot, this.weaponSpec, n3);
    //             float f23 = vector2f.x * vector2f3.x + vector2f2.x * vector2f3.y;
    //             float f24 = vector2f.y * vector2f3.x + vector2f2.y * vector2f3.y;
    //             this.\u00d5\u00d2\u00d8o00.setAngle(f22);
    //             this.\u00d5\u00d2\u00d8o00.setNormalBlend();
    //             this.\u00d5\u00d2\u00d8o00.setAlphaMult(f10);
    //             this.\u00d5\u00d2\u00d8o00.renderAtCenter(f12 + (f23 += 1.0f), f13 + f24);
    //             if (f11 > 0.0f) {
    //                 this.\u00d5\u00d2\u00d8o00.setAdditiveBlend();
    //                 this.\u00d5\u00d2\u00d8o00.setAlphaMult(f10 * f11);
    //                 this.\u00d5\u00d2\u00d8o00.renderAtCenter(f12 + f23, f13 + f24);
    //             }
    //             ++n3;
    //         }
    //         this.\u00d5\u00d2\u00d8o00.setSize(f19, f20);
    //     }
    //     GL11.glPopMatrix();
    // }

    // public void o00000(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Sprite sprite, float f10, float f11, boolean bl) {
    //     float f12;
    //     float f13 = (f2 + f4 + f6 + f8) / 4.0f;
    //     float f14 = (f3 + f5 + f7 + f9) / 4.0f;
    //     float f15 = (f8 - f2 + f6 - f4) / 2.0f;
    //     float f16 = (f5 - f3 + f7 - f9) / 2.0f;
    //     float f17 = sprite.getWidth() / (f15 += 10.0f);
    //     float f18 = sprite.getHeight() / (f16 += 10.0f);
    //     f17 *= this.\u00f4\u00d2\u00d8o00;
    //     f18 *= this.\u00f4\u00d2\u00d8o00;
    //     if (f17 > 1.0f) {
    //         f12 = f17 / f18;
    //         f17 = 1.0f;
    //         f18 = 1.0f / f12;
    //     }
    //     if (f18 > 1.0f) {
    //         f12 = f17 / f18;
    //         f18 = 1.0f;
    //         f17 = f12;
    //     }
    //     if (sprite == this.hardPointSprite) {
    //         this.return.int$private = f17;
    //         this.for.int$private = f18;
    //     }
    //     f12 = f13 + (f2 - f13) * f17;
    //     float f19 = f14 + (f3 - f14) * f18;
    //     float f20 = f13 + (f4 - f13) * f17;
    //     float f21 = f14 + (f5 - f14) * f18;
    //     float f22 = f13 + (f8 - f13) * f17;
    //     float f23 = f14 + (f9 - f14) * f18;
    //     float f24 = f13 + (f6 - f13) * f17;
    //     float f25 = f14 + (f7 - f14) * f18;
    //     sprite.setNormalBlend();
    //     sprite.setAlphaMult(f10);
    //     sprite.renderWithCorners(f12, f19, f20, f21, f24, f25, f22, f23);
    //     if (f11 > 0.0f) {
    //         sprite.setAdditiveBlend();
    //         sprite.setAlphaMult(f10 * f11);
    //         sprite.renderWithCorners(f12, f19, f20, f21, f24, f25, f22, f23);
    //     }
    //     if (sprite.getWidth() > 0.0f) {
    //         D d2 = new D();
    //         d2.o00000(true);
    //         Color color = B.o00000((Color)sprite.getColor(), (Color)Color.white, (float)0.33f);
    //         d2.o00000(color, color);
    //         float f26 = 1.0f;
    //         if (bl) {
    //             f26 = 0.75f;
    //         }
    //         f26 = 0.75f;
    //         d2.o00000(sprite, f12, f19, f20, f21, f24, f25, f22, f23, f10 * f26, bl);
    //     }
    // }

    // public WeaponSlotAPI null.private() {
    //     return this.weaponSlot;
    // }
}