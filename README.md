**Load Flow** 

This project was designed and implemented to achieve an easy to use load balance library.
That uses convention over configuration paradigm and you can setup your project just with some simple steps.

Furthermore, you can define your customized algorithm using annotated class.

    @Algorithm(value = "my-algorithm")
    class MyAlgorithm implements Distributor {
        public void distribute() {
            // Your algorithm
        }
    }

Then, wire these algorithms in your project and use it.

This project base annotations are as follows:
1) Message: Used to annotate message classes that can be received from any IN/OUT end points of network.
2) Processor: Used to annotate processor classes that can be used to transform message to desired formats or
 logging purpose and etc.
3) EndPoint: Used to annotate end point classes.
4) Algorithm: Algorithms which used to distribute message to different end points.
5) Pojo: This annotation is used to declare your project's POJOs. POJO is the class that support wiring functionality like `Component` classes in Spring project.

Also, you can find some abstract class/interfaces in this project that listed below:
1) Distributor: This interface implemented by algorithm classes to define distribute algorithm.
2) Element. 

The main class of this project is LoadFlowConfiguration that is first-class citizen and contains `run` method that used 
to initialize our load flow context.

The project contains `POJO` annotation that will be used to annotate classes  