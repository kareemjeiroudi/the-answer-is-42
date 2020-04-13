# AnswerIs42

<font face="chalkboard">Is a command line application that takes one argument and provides the answer to life, universe and everything.</font>

> the answer to life, universe and everything is 42
>
> https://www.youtube.com/watch?v=aboZctrHfK8

Users of this program have two options:

## 1. Add a Question

The following formatting is used to add a question:

```
<question>? "a" "b" "c"
```

where `a`,`b` and `c` are each an answer.
Answers are identified by a leading and trailing **straight** double-quotes `"`. Curly quotes used in good typography such as opening double-quote `“` and closing double quote  `”`  are Not handled. The sizing (characters lying outside the quotes) is irrelevant to the program. 

Empty answers are ignored. If the following example was given

```
question? "" "b" "c"
```

the answers to this question will be only `b` and `c`, the same applies to the following examples

```
question? "" "" "c" ""
// answer will be
* c
// or this 
question? "a" "     " "c" ""
// answer will be 
* a
* c
```

This is because when an answer is empty (of length 0) or is blank (contains only whitespaces), the constructor throws an `InvalidAnswerException`, which is then caught inside the program. 

Questions have to be entered as have been stored (no "best matches") - *according to the specification of this program*. However, you can enter a question with or without a question mark. I.e. `whoami?` is equivelant to `whoami`. The program will parse only those literals preceeding the question mark.

Moreover, both question and answers have a maximum length of **255 characters**. In the event where a specified question/answer exceeds the maximum length, an `InvalidQuestionException`/`InvalidAnswerException` is thrown by the constructor.

Questions must have at least one single answer in order for them to be stored. There's no upper bound to the number of answers a qeustion can have. 

### Input Processing

Any redundant question marks or whitespaces are trimmed off the input. This includes all leading and trailing whitespaces. e.g.

```
    question? "a" "b"     
// after processing
question? "a" "b"
```

as well as all leading and trailing question marks. e.g.

```
????     question? "a" "b" ?
// after processing
question? "a" "b"
```

Having said that, question marks are not allowed in a question statement, since they are used as delimiters to seprate a question from its answers. Examine the following example:

```
how can? I help you? "a" "b"
```

will result in chopping off ` I help you`, and the question stored wil look like `how can`. The same applies for answers and the use of quotes. Don't use quotes inside your answers, as this will make it look like as if your answers are shorter than really are.

E.g.

```
question? "he said "help me!"" "Jumped out of the window"
```

this will chop  `help me!` off the first answer

## 2. Ask a Question

A question is always identified by the literals found before the first occurence of a question mark. When asking a 
question It's important that you do not provide anything after a question mark in your input, as this will make it 
look like  **[adding a question](#1-add-a-question)**. Having done so, it will result in adding an invalid question, 
which <u>won't</u> be stored in the list of questions - unless of course it matches the formatting discussed earlier 
in [adding a question](#1-add-a-question). I.e.

```
Where's my car?     // is considered asking a question option ✔
Where's my car      // asking a question ✔
Where's             // asking a question ✔
?? Where            // asking a question ✔
?? Where ??         // asking a question ✔
Where's ? my car    // NOT asking a question -> adding a question ×   (1)
Where's ?   ?? ?    // asking a question ✔                            (2)
```

You can ask a question with or without a question mark: `Where's my car` is equivalent ot `Where's my car?`

Both are valid and considered the same question. Questions starting or ending with question marks are handled as mentioned earlier. A similar case can be found in `(2)`, where the question is ending with several question marks and spaces (See [Input Processing](#input-processing)).

*What happens in case of `(1)`?* the program recognizes the input as *Adding a quesiton* and parses answers. After failing to do so (no given answers), the question is then ignored (is not stored).

### Storing Question

Stored questions are not persistent. I.e. they are lost once the program teriminates. If the user asks a question that has already been stored, the program prints all answers mapped to that question, each on a separate line. If the user adds the same question with different answers, the answers are overwritten. Asking a question that has not yet been stored, the program prints “the answer to life, universe and everything is 42”.

