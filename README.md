**Load Flow** 

This project was designed and implemented to achieve an easy to use load balance library.
This project use convention over configuration paradigm and you can setup your project just with some simple steps.

Furthermore, you can define your customized algorithm using annotated class.

    @Algorithm(value = "my-algorithm")
    class MyAlgorithm implements Distributor {
        public void distribute() {
            // Your algorithm
        }
    }

Then, you can easily assign this algorithm on your application and use it.

This project base annotations are as follows:
1) Request: Used to annotate request classes that can be received from any IN end points of network.
2) Response: Used to annotate response classes that can be received from OUT end points.
3) Processor: Used to annotate processor classes that can be used to transform request or response to desired formats or
 logging purpose and etc.
4) EndPoint: Used to annotate end point classes.
5) Algorithm: Algorithms which used to distribute request/response to different end points.

Also, you can find some abstract class/interfaces in this project that listed below:
1) Distributor: This interface implemented by algorithm classes to define distribute algorithm.
2) Element: 

The main class of this project is LoadFlowConfiguration that is first-class citizen and contains `run` method that used 
to initialize our load flow context. 