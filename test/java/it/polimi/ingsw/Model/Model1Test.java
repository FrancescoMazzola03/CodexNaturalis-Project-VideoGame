package it.polimi.ingsw.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.Controller.GameController1;
import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.EventHandling.EventType;
import it.polimi.ingsw.EventHandling.JoinEvent1;
import it.polimi.ingsw.Listener.GameListener;
import it.polimi.ingsw.Network.ServerImpl1;
import junit.framework.TestCase;
import org.junit.Test;

public class Model1Test extends TestCase {

    GameListener listener;
    @Test
    public void testSetGame(){
        Game game = new Game(4);
        Model1 model = new Model1();
        model.setGame(game);
        assertEquals(model.getGame(),game);
    }
    @Test
    public void testGetGame(){
        Game game = new Game(4);
        Model1 model = new Model1();
        model.setGame(game);
        assertEquals(model.getGame(),game);
    }

    @Test
    public void testAddListener(){
        ServerImpl1 server = new ServerImpl1();
        JoinEvent1 joinEvent1 = new JoinEvent1("giorgio","paolo","111",3);
        JoinEvent1 joinEvent2 = new JoinEvent1("giorgione","paolo","115",3);
        GameController1 newController = new GameController1(new Model1());
        newController.getModel().addListener(joinEvent1.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent1.getId()).handleMessage(arg);
        });
        newController.getModel().addListener(joinEvent2.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent2.getId()).handleMessage(arg);
        });
        assertEquals(newController.getModel().getListeners().size(),2);
    }

    @Test
    public void testGetListenerByName(){
        ServerImpl1 server = new ServerImpl1();
        JoinEvent1 joinEvent1 = new JoinEvent1("giorgio","paolo","111",3);
        GameController1 newController = new GameController1(new Model1());
        newController.getModel().addListener(joinEvent1.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent1.getId()).handleMessage(arg);
        });
        assertNotNull(newController.getModel().getListenerByName("giorgio"));
    }
    @Test
    public void testRemoveListener(){
        ServerImpl1 server = new ServerImpl1();
        JoinEvent1 joinEvent1 = new JoinEvent1("giorgio","paolo","111",3);
        GameController1 newController = new GameController1(new Model1());
        newController.getModel().addListener(joinEvent1.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent1.getId()).handleMessage(arg);
        });
        assertNotNull(newController.getModel().getListenerByName("giorgio"));
        newController.getModel().removeListener("giorgio");
        assertNull(newController.getModel().getListenerByName("giorgio"));
    }

    @Test
    public void testNotifyListener(){

        TestGameListener listener = new TestGameListener();
        Model1 model = new Model1();
        model.setGame(new Game(4));
        ServerImpl1 server = new ServerImpl1();
        JoinEvent1 joinEvent1 = new JoinEvent1("giorgio","paolo","111",3);
        GameController1 newController = new GameController1(model);
        newController.getModel().addListener(joinEvent1.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent1.getId()).handleMessage(arg);
        });
        newController.getModel().notifyListener(listener,new Event(EventType.USERNAME,"giorgio"),"giorgione");
        assertEquals(listener.getReceivedEvent().getType(),EventType.USERNAME);
        newController.getModel().notifyListener(listener,null,"giorgione");
        assertEquals(listener.getReceivedEvent().getType(),EventType.REFRESH);
    }

   /* @Test
    public void testNotifyAllListeners(){


        TestGameListener listener = new TestGameListener();
        ServerImpl1 server = new ServerImpl1();
        JoinEvent1 joinEvent1 = new JoinEvent1("giorgio","paolo","111",4);
        JoinEvent1 joinEvent2 = new JoinEvent1("giorgione","paolo","112",4);
        JoinEvent1 joinEvent3 = new JoinEvent1("giorgionene","paolo","113",4);
        GameController1 newController = new GameController1(new Model1());
        newController.getModel().addListener(joinEvent1.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent1.getId()).handleMessage(arg);
        });
        newController.getModel().addListener(joinEvent2.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent2.getId()).handleMessage(arg);
        });
        newController.getModel().addListener(joinEvent3.getUsername(),(arg) -> {
            server.getController().getListenerByName(joinEvent3.getId()).handleMessage(arg);
        });

        System.out.println(newController.getModel().getListeners().size());
        //newController.getModel().notifyAllListeners(new Event(EventType.USERNAME,"giorgione"),"giorgione");
        TestGameListener list1 = null;
        for(TestGameListener list :  (TestGameListener[]) newController.getModel().getListeners().values())
             list1 = (TestGameListener) list;
            assertEquals(list1.getReceivedEvent(),EventType.USERNAME);
    }*/
}
