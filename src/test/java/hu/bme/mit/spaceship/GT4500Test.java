package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init(){
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);
    this.ship = new GT4500(primary,secondary);
  }
  
  @Test
  public void test_default_ship() {
    GT4500 defShip = new GT4500();
    
    boolean result = defShip.fireTorpedo(FiringMode.SINGLE);
     assertEquals(true, result);
    
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    // Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);
    
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }
  
  /*
  Sajat tesztesetek:
  	- valoban a primary az elso fire-e
  	- ketszer lehet-e fire-olni a primary-t
  	- alternalas kikerulese ha ures a masik
  	- failure-re nem akar masikat kiloni
  	- ures tarral tud-e loni
 */
 
 @Test
  public void fireTorpedo_Firing_Order(){
    // Arrange
    TorpedoStore pr = new TorpedoStore(1);
    TorpedoStore sc = new TorpedoStore(2);
    GT4500 tempShip = new GT4500(pr,sc);

    // Act
    boolean result = tempShip.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, pr.isEmpty());
    assertEquals(false,  sc.isEmpty());
  }
  
  @Test
  public void fireTorpedo_Primary_Twice(){
    // Arrange
    TorpedoStore pr = new TorpedoStore(2);
    TorpedoStore sc = new TorpedoStore(1);
    GT4500 tempShip = new GT4500(pr,sc);

    // Act
    boolean result = tempShip.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = tempShip.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(true,  result2);
    assertEquals(false, pr.isEmpty());
  }
  
  @Test
  public void fireTorpedo_Skip_Alternating(){
    // Arrange
    TorpedoStore pr = new TorpedoStore(2);
    TorpedoStore sc = new TorpedoStore(0);
    GT4500 tempShip = new GT4500(pr,sc);

    // Act
    boolean result = tempShip.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = tempShip.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(true,  result2);
    assertEquals(true, pr.isEmpty());
  }
  
  @Test
  public void fireTorpedo_Stop_At_Failure(){
    // Arrange
    TorpedoStore pr = new TorpedoStore(0);
    TorpedoStore sc = new TorpedoStore(0);
    GT4500 tempShip = new GT4500(pr,sc);

    // Act
    boolean result = tempShip.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = tempShip.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    assertEquals(false,  result2);
  }
  
  @Test
  public void fireTorpedo_Empty(){
    // Arrange
    TorpedoStore pr = new TorpedoStore(0);
    TorpedoStore sc = new TorpedoStore(0);
    GT4500 tempShip = new GT4500(pr,sc);

    // Act
    boolean result = tempShip.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = tempShip.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    assertEquals(false,  result2);
  }
  	

}
