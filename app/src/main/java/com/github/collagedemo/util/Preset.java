package com.github.collagedemo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;

/**
* @author Sergey Ryabov
*         Date: 13.03.14
*/
public enum Preset {

    TWO(2) {
        public Pair<Rect, RectF> getRects(Bitmap b, Canvas c, int pos) {
            final Rect CENTERED_STRIP_IMAGE = new Rect(b.getWidth() / 4, 0, b.getWidth() *3 / 4, b.getHeight());
            switch (pos) {
                case 0:
                    return Pair.create(
                            CENTERED_STRIP_IMAGE,
                            new RectF(0, 0, c.getWidth() / 2, c.getHeight()));
                case 1:
                    return Pair.create(
                            CENTERED_STRIP_IMAGE,
                            new RectF(c.getWidth() / 2, 0, c.getWidth(), c.getHeight()));
                default:
                    throw new IndexOutOfBoundsException("Invalid position: " + pos);
            }

        }
    }, 
    THREE(3) {
        public Pair<Rect, RectF> getRects(Bitmap b, Canvas c, int pos) {
            final Rect WHOLE_IMAGE = new Rect(0, 0, b.getWidth(), b.getHeight());
            switch (pos) {
                case 0:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(0, 0, c.getWidth() / 2, c.getHeight() / 2));
                case 1:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 2, 0, c.getWidth(), c.getHeight() / 2));
                case 2:
                    return Pair.create(
                            new Rect(0, b.getHeight() / 4, b.getWidth(), b.getHeight() * 3 / 4),
                            new RectF(0, c.getHeight() / 2, c.getWidth(), c.getHeight()));
                default:
                    throw new IndexOutOfBoundsException("Invalid position: " + pos);
            }

        }
    },
    FOUR(4) {
        public Pair<Rect, RectF> getRects(Bitmap b, Canvas c, int pos) {
            final Rect WHOLE_IMAGE = new Rect(0, 0, b.getWidth(), b.getHeight());
            switch (pos) {
                case 0:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(0, 0, c.getWidth() / 2, c.getHeight() / 2));
                case 1:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 2, 0, c.getWidth(), c.getHeight() / 2));
                case 2:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(0, c.getHeight() / 2, c.getWidth() / 2, c.getHeight()));
                case 3:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 2, c.getHeight() / 2, c.getWidth(), c.getHeight()));
                default:
                    throw new IndexOutOfBoundsException("Invalid position: " + pos);
            }

        }
    },
    FIVE(5) {
        public Pair<Rect, RectF> getRects(Bitmap b, Canvas c, int pos) {
            final Rect WHOLE_IMAGE = new Rect(0, 0, b.getWidth(), b.getHeight());
            switch (pos) {
                case 0:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(0, 0, c.getWidth() / 2, c.getHeight() / 2));
                case 1:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 2, 0, c.getWidth(), c.getHeight() / 2));
                case 2:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(0, c.getHeight() / 2, c.getWidth() / 2, c.getHeight()));
                case 3:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 2, c.getHeight() / 2, c.getWidth(), c.getHeight()));
                case 4:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 4, c.getHeight() / 4, c.getWidth() * 3 / 4, c.getHeight() * 3 / 4));
                default:
                    throw new IndexOutOfBoundsException("Invalid position: " + pos);
            }

        }
    },
    SIX(6) {
        public Pair<Rect, RectF> getRects(Bitmap b, Canvas c, int pos) {
            final Rect WHOLE_IMAGE = new Rect(b.getWidth() / 6, 0, b.getWidth() * 5 / 6, b.getHeight());
            switch (pos) {
                case 0:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(0, 0, c.getWidth() / 3, c.getHeight() / 2));
                case 1:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 3, 0, c.getWidth() * 2 / 3, c.getHeight() / 2));
                case 2:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() * 2 / 3, 0, c.getWidth(), c.getHeight() / 2));
                case 3:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(0, c.getHeight() / 2, c.getWidth() / 3, c.getHeight()));
                case 4:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() / 3, c.getHeight() / 2, c.getWidth() * 2 / 3, c.getHeight()));
                case 5:
                    return Pair.create(
                            WHOLE_IMAGE,
                            new RectF(c.getWidth() * 2 / 3, c.getHeight() / 2, c.getWidth(), c.getHeight()));
                default:
                    throw new IndexOutOfBoundsException("Invalid position: " + pos);
            }

        }
    };

    private int count;
    
    Preset(int count) {
        this.count = count;
    }

    public abstract Pair<Rect, RectF> getRects(Bitmap b, Canvas c, int pos);

    public static Preset valueOf(int count) {
        for (Preset preset : values()) {
            if (preset.count == count) return preset;
        }
        throw new IllegalArgumentException("no such preset for " + count + " bitmaps");
    }
}
