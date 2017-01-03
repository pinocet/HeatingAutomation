package org.cata.homeautomation;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.cata.homeautomation.contracts.IHeatingController;
import org.cata.homeautomation.dataobjects.TempChangedEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampRouter;
import ws.wamp.jawampa.WampRouterBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;
import ws.wamp.jawampa.transport.netty.SimpleWampWebsocketListener;

public class HeatingAutomationApi {
	
	private EventBus _eventBus;
	private IHeatingController _heatingController;
	
	private WampClient client;
	
	private WampClient testClient;
	private Subscription eventSubscription;
	
	private WampRouter router;
	private SimpleWampWebsocketListener server;
	
	public HeatingAutomationApi(EventBus eventBus, IHeatingController heatingController) {
		_eventBus = eventBus;
		_heatingController = heatingController;
	}
	
	@Subscribe public void HandleTempChangedEvent(TempChangedEvent e) {
	    client.publish("homeautomation.events.tempchanged", e.getCurrentTemperature());
	}
	
	public void SetupRouter() {
		WampRouterBuilder routerBuilder = new WampRouterBuilder();
        try {
            routerBuilder.addRealm("realm1");
            router = routerBuilder.build();
        } catch (ApplicationError e1) {
            e1.printStackTrace();
            return;
        }
        
        URI serverUri = URI.create("ws://0.0.0.0:8086/ws1");

        try {
            server = new SimpleWampWebsocketListener(router, serverUri, null);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
	}
	
	public void SetupClient() {
        IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();
        WampClientBuilder builder = new WampClientBuilder();

        try {
            builder.withConnectorProvider(connectorProvider)
                   .withUri("ws://localhost:8086/ws1")
                   .withRealm("realm1")
                   .withInfiniteReconnects()
                   .withReconnectInterval(3, TimeUnit.SECONDS);
            client = builder.build();
            
            testClient = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        testClient.statusChanged().subscribe(new Action1<WampClient.State>() {
            @Override
            public void call(WampClient.State t1) {
                System.out.println("Session2 status changed to " + t1);

                if (t1 instanceof WampClient.ConnectedState) {
        
        eventSubscription = testClient.makeSubscription("homeautomation.events.tempchanged", Double.class)
                .subscribe(new Action1<Double>() {
					@Override
					public void call(Double t1) {
					System.out.println("Received event test.event with value " + t1);
					}
					}, new Action1<Throwable>() {
					@Override
					public void call(Throwable t1) {
					System.out.println("Completed event test.event with error " + t1);
					}
					}, new Action0() {
					@Override
					public void call() {
					System.out.println("Completed event test.event");
					}
					});
                };
            }});
        
        client.open();
        testClient.open();
	}

	public void Dispose() {
		System.out.println("Closing router");
        router.close().toBlocking().last();
        server.stop();
        
        System.out.println("Closing the client 1");
        client.close().toBlocking().last();
        
        System.out.println("Closing the client 2");
        testClient.close().toBlocking().last();
		
	}
}
