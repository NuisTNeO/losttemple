package cn.com.hotmaze.util;


/**
 * @author singerinsky
 *
 *
 * A 2D  vector
 */
public class Vec2 {
        public float x, y;

        public Vec2() {
                this(0, 0);
        }

        public Vec2(float x, float y) {
                this.x = x;
                this.y = y;
                // testbed.PTest.debugCount++;
        }

        public Vec2( Vec2 toCopy) {
                this(toCopy.x, toCopy.y);
        }

        public final void reSet() {
                x = 0.0f;
                y = 0.0f;
        }

        /** Set the vector component-wise. */
        public final void set(float x, float y) {
                this.x = x;
                this.y = y;
        }

        /** Set this vector to another vector. */
        public final Vec2 set(Vec2 v) {
                this.x = v.x;
                this.y = v.y;
                return this;
        }

        /** Return the sum of this vector and another; does not alter either one. */
        public final Vec2 add(Vec2 v) {
                return new Vec2(x + v.x, y + v.y);
        }
        
        

        /** Return the difference of this vector and another; does not alter either one. */
        public final Vec2 sub(Vec2 v) {
                return new Vec2(x - v.x, y - v.y);
        }

        /** Return this vector multiplied by a scalar; does not alter this vector. */
        public final Vec2 mul(float a) {
                return new Vec2(x * a, y * a);
        }

        /** Return the negation of this vector; does not alter this vector. */
        public final Vec2 negate() {
                return new Vec2(-x, -y);
        }

        /** Flip the vector and return it - alters this vector. */
        public final Vec2 negateLocal() {
                x = -x;
                y = -y;
                return this;
        }

        /** Add another vector to this one and returns result - alters this vector. */
        public final Vec2 addLocal(Vec2 v) {
                x += v.x;
                y += v.y;
                return this;
        }
        
        /** Adds values to this vector and returns result - alters this vector. */
        public final Vec2 addLocal( float x, float y) {
                this.x+=x;
                this.y+=y;
                return this;
        }

        /** Subtract another vector from this one and return result - alters this vector. */
        public final Vec2 subLocal(Vec2 v) {
                x -= v.x;
                y -= v.y;
                return this;
        }

        /** Multiply this vector by a number and return result - alters this vector. */
        public final Vec2 mulLocal(float a) {
                x *= a;
                y *= a;
                return this;
        }

        /** Return the length of this vector. */
        public final float length() {
                return (float) Math.sqrt((new Float(x*x+y*y)).doubleValue());
        }

        /** Return the squared length of this vector. */
        public final float lengthSquared() {
                return (x*x + y*y);
        }

        /** ¹æ¸ñ»¯ Normalize this vector and return the length before normalization.  Alters this vector. */
        public final float normalize() {
                float length = length();
                float invLength = 1.0f / length;
                x *= invLength;
                y *= invLength;
                return length;
        }

        /** True if the vector represents a pair of valid, non-infinite floating point numbers. */
        public final boolean isValid() {
                return x != Float.NaN && x != Float.NEGATIVE_INFINITY
                && x != Float.POSITIVE_INFINITY && y != Float.NaN
                && y != Float.NEGATIVE_INFINITY && y != Float.POSITIVE_INFINITY;
        }

        /** Return a new vector that has positive components. */
        public final Vec2 abs() {
                return new Vec2(Math.abs(x), Math.abs(y));
        }

        /* djm created */
        public final void absLocal(){
                x = Math.abs(x);
                y = Math.abs(y);
        }

        @Override
        /** Return a copy of this vector. */
        public final Vec2 clone() {
                return new Vec2(x, y);
        }

        @Override
        public final String toString() {
                return "(" + x + "," + y + ")";
        }

        /*
         * Static
         */

        public final static Vec2 abs(Vec2 a) {
                return new Vec2(Math.abs(a.x), Math.abs(a.y));
        }

        /* djm created */
        public final static void absToOut(Vec2 a, Vec2 out){
                out.x = Math.abs( a.x);
                out.y = Math.abs( a.y);
        }

        public final static float dot(Vec2 a, Vec2 b) {
                return a.x * b.x + a.y * b.y;
        }

        public final static float cross(Vec2 a, Vec2 b) {
                return a.x * b.y - a.y * b.x;
        }

        public final static Vec2 cross(Vec2 a, float s) {
                return new Vec2(s * a.y, -s * a.x);
        }

        /* djm created */
        public final static void crossToOut(Vec2 a, float s, Vec2 out){
                float tempy = -s * a.x;
                out.x = s * a.y;
                out.y = tempy;
        }

        public final static Vec2 cross(float s, Vec2 a) {
                return new Vec2(-s * a.y, s * a.x);
        }

        /* djm created */
        public final static void crossToOut(float s, Vec2 a, Vec2 out){
                float tempY = s * a.x;
                out.x = -s * a.y;
                out.y = tempY;
        }
        
        public final static void negateToOut(Vec2 a, Vec2 out){
                out.x = -a.x;
                out.y = -a.y;
        }

        public final static Vec2 min(Vec2 a, Vec2 b) {
                return new Vec2(a.x < b.x ? a.x : b.x, a.y < b.y ? a.y : b.y);
        }

        public final static Vec2 max(Vec2 a, Vec2 b) {
                return new Vec2(a.x > b.x ? a.x : b.x, a.y > b.y ? a.y : b.y);
        }

        /* djm created */
        public final static void minToOut(Vec2 a, Vec2 b, Vec2 out) {
                out.x = a.x < b.x ? a.x : b.x;
                out.y = a.y < b.y ? a.y : b.y;
        }

        /* djm created */
        public final static void maxToOut(Vec2 a, Vec2 b, Vec2 out) {
                out.x = a.x > b.x ? a.x : b.x;
                out.y = a.y > b.y ? a.y : b.y;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() { //automatically generated by Eclipse
                final int prime = 31;
                int result = 1;
                result = prime * result + Float.floatToIntBits(x);
                result = prime * result + Float.floatToIntBits(y);
                return result;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) { //automatically generated by Eclipse
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Vec2 other = (Vec2) obj;
                if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
                        return false;
                if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
                        return false;
                return true;
        }
        
        public double distanceToTarget(Vec2 target){
        	return Math.sqrt((this.x - target.x)*(this.x - target.x) + (this.y - target.y)*(this.y - target.y));
        }
        
}