package bombgame.controller.config;

import bombgame.controller.config.GameHandlerConfiguration;
import junit.framework.TestCase;

public final class GameHandlerConfigurationTest extends TestCase {

	GameHandlerConfiguration config;
	GameHandlerConfiguration config2;
	
	public void setUp() {
		config = new GameHandlerConfiguration(0, 0, 0);
		config2 = new GameHandlerConfiguration(30, 20, 3);
	}
	
	public void testSetterGetter() {
		config.setFieldWidth(60);
		config.setFieldHeight(30);
		config.setNumberOfAIs(3);
		
		assertEquals(config.getFieldWidth(), 60);
		assertEquals(config.getFieldHeight(), 30);
		assertEquals(config.getNumberOfAIs(), 3);
	}
	
	public void testCheckConfiguration() {
		config.setFieldWidth(-1);
		config.setFieldHeight(-1);
		config.setNumberOfAIs(-1);
		assertFalse(config.checkConfiguration());
		
		config.setFieldWidth(60);
		config.setFieldHeight(30);
		config.setNumberOfAIs(3);
		assertTrue(config.checkConfiguration());
		
		config.setFieldWidth(GameHandlerConfiguration.MAX_WIDTH+1);
		config.setFieldHeight(GameHandlerConfiguration.MAX_HEIGHT+1);
		config.setNumberOfAIs(GameHandlerConfiguration.MAXAI+1);
		assertFalse(config.checkConfiguration());
	}
	
	public void testCreateSingelGameHandler() {
		assertNotNull(config2.createSingleGameHandler());
		assertNotNull(config2.createSingleGameHandler());
	}
	
	public void testCreateGameHandler() {
		config.setFieldWidth(60);
		config.setFieldHeight(30);
		config.setNumberOfAIs(-1);
		
		try {
			config.createGameHandler();
		} catch(IllegalArgumentException e) {
			assertEquals("GameHandler configuration is not correct.", e.getMessage());
		}
		
		config.setNumberOfAIs(3);
		
		assertNotNull(config.createGameHandler());
	}
	
}
