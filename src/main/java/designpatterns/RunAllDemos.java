package designpatterns;

import designpatterns.behavioral.chainofresponsibility.ChainOfResponsibilityDemo;
import designpatterns.behavioral.command.CommandDemo;
import designpatterns.behavioral.interpreter.InterpreterDemo;
import designpatterns.behavioral.iterator.IteratorDemo;
import designpatterns.behavioral.mediator.MediatorDemo;
import designpatterns.behavioral.memento.MementoDemo;
import designpatterns.behavioral.observer.ObserverDemo;
import designpatterns.behavioral.state.StateDemo;
import designpatterns.behavioral.strategy.StrategyDemo;
import designpatterns.behavioral.templatemethod.TemplateMethodDemo;
import designpatterns.behavioral.visitor.VisitorDemo;
import designpatterns.creational.abstractfactory.AbstractFactoryDemo;
import designpatterns.creational.builder.BuilderDemo;
import designpatterns.creational.factorymethod.FactoryMethodDemo;
import designpatterns.creational.prototype.PrototypeDemo;
import designpatterns.creational.singleton.SingletonDemo;
import designpatterns.structural.adapter.AdapterDemo;
import designpatterns.structural.bridge.BridgeDemo;
import designpatterns.structural.composite.CompositeDemo;
import designpatterns.structural.decorator.DecoratorDemo;
import designpatterns.structural.facade.FacadeDemo;
import designpatterns.structural.flyweight.FlyweightDemo;
import designpatterns.structural.proxy.ProxyDemo;

/**
 * Runs the demo of every pattern one after another:
 * {@code ./mvnw -q compile exec:java}
 */
public class RunAllDemos {

    public static void main(String[] args) throws Exception {
        String[] none = {};

        // Creational
        SingletonDemo.main(none);
        FactoryMethodDemo.main(none);
        AbstractFactoryDemo.main(none);
        BuilderDemo.main(none);
        PrototypeDemo.main(none);

        // Structural
        AdapterDemo.main(none);
        BridgeDemo.main(none);
        CompositeDemo.main(none);
        DecoratorDemo.main(none);
        FacadeDemo.main(none);
        FlyweightDemo.main(none);
        ProxyDemo.main(none);

        // Behavioral
        ChainOfResponsibilityDemo.main(none);
        CommandDemo.main(none);
        InterpreterDemo.main(none);
        IteratorDemo.main(none);
        MediatorDemo.main(none);
        MementoDemo.main(none);
        ObserverDemo.main(none);
        StateDemo.main(none);
        StrategyDemo.main(none);
        TemplateMethodDemo.main(none);
        VisitorDemo.main(none);
    }
}
