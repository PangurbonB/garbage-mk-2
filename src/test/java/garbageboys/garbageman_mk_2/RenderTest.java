package garbageboys.garbageman_mk_2;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import garbageboys.garbageman_mk_2.Rendering.GarbageRenderer;
import garbageboys.garbageman_mk_2.Rendering.Render2D;


public class RenderTest extends TestCase{
    GarbageRenderer rendererTest = new GarbageRenderer();
    void testRotatePair() {
        rendererTest.rotatePair(-2f,-3f, (float)Math.PI, 0f, 0f);
        
        rendererTest.rotatePair(-2f,-3f, (float)Math.PI/2, 0f, 0f);
        rendererTest.rotatePair(-2f,-3f, (float)Math.PI/4, 0f, 0f);
        
    }
    
}
