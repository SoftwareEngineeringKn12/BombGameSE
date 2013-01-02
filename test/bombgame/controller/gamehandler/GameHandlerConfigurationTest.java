package bombgame.controller.gamehandler;

import junit.framework.TestCase;

public final class GameHandlerConfigurationTest extends TestCase {

	GameHandlerConfiguration config;
	
	public void setUp() {
		config = new GameHandlerConfiguration(0, 0, 0);
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
