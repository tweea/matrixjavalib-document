[TOC]

# 总则

1. 定义这个规范的目的是让项目中所有的代码看起来都像一个人写的，增加可读性，减少项目组中因为换人而带来的损失。

2. 这些规范可以适当变通，但是一定要让程序有良好的可读性，同时在注释中记录不遵循规范的原因。

3. 我们根据这里定义的代码格式化规范建立了 Eclipse 的自定义格式化代码配置文件，请在使用的 Eclipse 中导入这个配置文件，并设置保存时自动格式化修改过的代码。

4. 我们在代码中统一使用中文作为注释语言。

5. 所有 Java 源文件统一使用 **UTF-8** 编码保存。

# 文件格式

所有的 Java 源文件都必须遵守如下的格式规则：

## 1. 版权信息

所有 Java 源文件的开头都必须声明版权信息，如：

```java
/*
 * 版权所有 2016 Matrix。
 * 保留所有权利。
 */
```

其他必要的声明信息可以写在版权信息后面。

## 2. package/import

package 应以 net.matrix 开头，后面应是项目的名称或缩写，再后面的命名根据项目的设计决定。

```java
package net.matrix.crm.util;
```

import 语句应紧跟在 package 语句后面，中间有一个空行分隔。每个 import 语句应独占一行。多条 import 语句应按照包名大类分组，不同分组间用一个空行分隔，每个分组中按照包和类的字母顺序排列，标准的包分组（java/javax）要在其他的包分组之前。应引用具体的类（import java.util.Set），而不引用包（import java.util.\*）。

```java
package net.matrix.stats;

import java.io.Reader;
import java.util.Observable;

import net.sf.util.Cat;
import net.sf.util.Dog;
```

我们建立了 Eclipse 的导入顺序配置文件，请在使用的 Eclipse 中导入这个配置文件。

## 3. 类的声明

必要时应有 JavaDoc 说明解释类的含义和用途。extends 关键字应在 implements 前面。

```java
/**
 * 狗，一种动物。
 */
public class Dog extends Animal implements Cloneable
```

## 4. 成员变量

两个成员变量中间有一个空行分隔。成员变量的排序：首先根据实例化方式，静态成员变量在前，实例成员变量在后；然后根据可见性，public 在前，private 在后。

```java
public static final String FOO = "foo";

protected static final String BAR = "bar";

private static final String ZOO = "zoo";

protected String some;

private String thing;
```

当一个字符串或其它类型的常量在多个地方使用，必要时将其定义为静态常量。

```java
private static final SOME_CLASS_NAME = "com.foo.bar.Provider";
```

public 的成员变量必须编写 JavaDoc。protected、private 和 package 的成员变量如果意义明确，可以没有注释。

```java
/**
 * Packet counters
 */
protected int[] packets;
```

## 5. 构造方法

构造方法应使用递增的顺序排列，参数少的在前面，参数多的在后面。

```java
public CounterSet() {
    this(3);
}

public CounterSet(int size) {
    this.size = size;
}
```

## 6. clone 方法和 finalize 方法

尽量避免使用 clone() 方法。

尽量避免使用 finalize 方法。

```java
@Override
public Object clone() throws CloneNotSupportedException {
    CounterSet obj = (CounterSet) super.clone();
    obj.packets = (int[]) packets.clone();
    obj.size = size;
    return obj;
}

@Override
protected void finalize() throws Throwable {
    super.finalize();
}
```

## 7. 存取方法

构造方法后面是成员变量的存取方法。简单的存取方法可以没有 JavaDoc。

```java
public int[] getPackets() {
    return packets;
}

public void setPackets(int[] packets) {
    this.packets = packets;
}
```

## 8. 其它方法

存取方法后面是其它方法。在这些其它方法中，实现接口或重写父类的方法在前面，普通方法在后面，但重写 Object 对象定义的 equals(Object)、hashCode()、toString() 方法在普通方法后面。这些方法应按照功能划分不同分组并排序。分组中按照方法调用关系排序，被其他类调用的公共方法在前，被内部其它方法调用的方法在后，不同的公共方法间也应按照其调用关系排序，被调用方法应尽量靠近其调用者后面，但在整个类中较为常用的被调用方法可以在类的后部形成公共方法区。同名方法应使用递增的顺序排列，参数少的在前面，参数多的在后面。

应当限制方法的作用域，只在类内部使用的方法应为 private，有子类使用的方法为 protected，尽量避免内部方法定义为 public。

public 的方法必须编写 JavaDoc。protected、private 和 package 的成员变量如果意义明确，可以没有注释。但实现接口或重写父类的方法一般不需要单独编写 JavaDoc。

```java
@Override
public cook() {
    cookRice();
    cookVegetable();
    cookSoup();
}

private cookRice() {
}

private cookVegetable() {
}

private cookSoup() {
}
```

## 9. main 方法

如果定义 public static void main(String[]) 方法, 那么它应该写在类的底部。

## 10. 示例

```java
/*
 * 版权所有 2016 Matrix。
 * 保留所有权利。
 */
package net.matrix.kitchen;

import net.matrix.world.Human;

/**
 * 厨师。
 */
public class Cook extends Human {
    /**
     * 一口锅
     */
    private Pan pan;

    /**
     * 生而能做饭。
     */
    public Cook() {
    }

    /**
     * 生而有锅。
     */
    public Cook(Pan pan) {
        this.pan = pan;
    }

    public Pan getPan() {
        return pan;
    }

    public void setPan(Pan pan) {
        this.pan = pan;
    }

    /**
     * 做饭。
     */
    public cook() {
        cookRice();
        cookVegetable();
        cookSoup();
    }

    private cookRice() {
        pan.cook(new Rice());
    }

    private cookVegetable() {
        pan.cook(new Vegetable());
    }

    private cookSoup() {
        pan.cook(new Soup());
    }
}
```

# 排版规范

1\. 单目操作符与操作数之间不加空格，双目操作符两侧加一空格，取成员操作符不加空格。大中小括号内侧不加空格，括号间不加空格，关键字与括号间加一空格。

2\. 相对独立的程序块之间加一空行。

3\. 代码缩进使用空格（四字符宽度）。

4\. 较长的语句、表达式等要分成多行书写。划分出的新行要进行适当的缩进，使排版整齐，语句可读。长表达式要在低优先级操作符处划分新行，操作符放在新行之首。

5\. 禁止一行中写多条语句，即一行只写一条语句。

6\. 循环、判断等语句块中的代码都要采用缩进风格。

```java
if (a == b) {
    x = 2;
} else {
    x = 1;
}

for (int i = 0; i < 4; i++) {
    x += i;
}

do {
    x++;
} while (flag);

while (flag) {
    x++;
}

switch (a) {
case 1:
    b = 2;
    break;
default:
}

try {
    x = 1;
} catch (Exception e) {
    x = 2;
} finally {
    x = 3;
}
```

7\. 使用大括号 ‘{’ 和 ‘}’ 界定程序块时 ‘{’ 和 ‘}’ 应各独占一行并且位于同一列。

```java
a = b;
{
    x = 2;
}
```

8\. 内部类多缩进一层。

```java
public class A {
    public static class B {
    }
}
```

# 代码规范

1\. 命名

在大部分情况下不需要使用匈牙利表示法。不要在命名中加前缀，如 o\_, obj\_, m\_ 之类。

package 的命名必须所有字母小写。

```java
package com.neu.util
```

class 的命名必须所有单词首字母大写而其他字母都小写。

```java
public class TheClassName {
}
```

变量的命名必须第一个单词全部小写，后面所有单词首字母大写而其他字母都小写。

```java
userName, theClassMethodName
```

static final 静态常量的命名必须全部大写，单词间用下划线分隔。

```java
/**
 * 数据库配置文件路径。
 */
public static final String DB_CONFIG_FILE_PATH = "com.neu.etrain.dbconfig";
```

构造方法或存取方法的参数命名应与对应成员变量命名一致。

```java
public List(int size) {
    this.size = size;
}

public void setSize(int size) {
    this.size = size;
}
```

在不同级别的代码范围内使用足够短和足够长的命名，如：条件或循环变量 1 个单词，方法名 1-2 个单词，类名 2-3 个单词，全局变量 3-4 个单词。

命名要有具体的意义，能大概反映出含义和用途。不要滥用 value、data 这样的抽象命名。

相似概念的命名应在形式上保持一致。

```java
dogName, catName, ratName
```

不要在同一个类的不同上下文中重用相同的变量名。

```java
private int size;

public void someMethod() {
    // 本地变量掩盖了成员变量
    int size = ...;
}
```

不要在同一个方法内用同一个变量做不同的用途。

```java
int size = list.size();
...
size = array.length;
```

命名时不要使用汉语拼音。

```java
hu.yao(Object mubiao)
```

方法命名应以动词开始，其他命名应是名词。

```java
tiger.bite(Object target)
```

2\. JavaDoc

应当使用 JavaDoc 来生成文档，不仅因为它是标准，也是因为它是各种 IDE 和工具都支持的格式。

不推荐使用 JavaDoc 的 ```@author``` 标记，因为代码不是被个人拥有的，而且记录的作者信息并不总是准确的。

3\. 数组声明

数组声明应使用下面格式：

```java
byte[] buffer;
```

而不是：

```java
byte buffer[];
```

4\. 尽量使用泛型，以明确类型信息减少编码错误。

```java
List<String> names;
```

5\. 在废弃的代码增加 ```@Deprecated``` 注解，避免其它代码继续使用。在 JavaDoc 中增加 ```@deprecated``` 标记说明废弃的原因和替代方法。

```java
/**
 * @deprecated 原因某某某，使用 newMethod() 代替
 */
@Deprecated
public void oldMethod() {
}
```

6\. 在实现接口或重写方法时使用 ```@Override``` 注解，避免因疏忽产生的编码错误。

```java
public class A {
    public void a1() {
    }

    public void b1() {
    }
}

public class B extends A {
    // 没有编译错误
    public void a2() {
    }

    // 编译错误
    @Override
    public void b2() {
    }
}
```

7\. 禁止使用 ```@SuppressWarnings``` 注解，避免掩盖问题。

8\. 日期时间数据以字符串形式表示时使用 yyyy-MM-dd 和 yyyy-MM-dd'T'HH-mm-ss 作为标准格式。在日志记录和数据传输时应使用标准格式。

9\. 使用统一的日志框架记录日志信息，禁止使用 System.out、System.err 和 Throwable.printTraceStack()。

日志的级别从低到高分别为 TRACE < DEBUG < INFO < WARN < ERROR < FATAL。一般的日志信息使用 INFO 级别，调试信息等次要信息使用较低级别，错误信息等重要信息使用较高级别。TRACE 级别是最详尽的调试信息，通常用在程序遇到问题需要仔细分析运行状况时；DEBUG 级别是较关键的调试信息，通常用在程序测试运行时；WARN 级别表示出现了一些问题，但不影响程序运行；ERROR 级别表示程序运行出现了错误；FATAL 级别表示程序运行出现严重错误，已不能正常运行。

日志信息要简洁并有意义。

根据日志级别及日志输出语句复杂度，可以在日志输出语句前增加日志级别判断，减少不必要的资源开销。

```java
if (LOG.isTraceEnabled()) {
    LOG.trace("一些生成过程复杂的信息：{}", generateComplexInfo());
}
```

10\. 将常量或已知量放在 equals 方法前面，避免空指针错误。

```java
String itemName = null;
// false
"abc".equals(itemName)
// NullPointerException
itemName.equals("abc")
```

# 常见问题

1\. 不必要的字符串复制

有一种不可变对象，它的内部状态是在对象构造时决定的，构造以后无法改变。不可变对象的内部状态是不可改变的，因此不需要复制它。任何对这种对象的复制都是多余的，而在测试时很难发现这种问题。最常用的不可变对象是 String。

下面的代码会正常工作：

```java
// 用匿名字符串构造 String 对象
String temp = new String("Text here");
// 用命名字符串构造 String 对象
String s = new String(temp);
```

但是，这样的代码性能较差，而且没必要这么复杂。你可以用以下的方式来重写上面的代码：

```java
String temp = "Text here";
String s = temp;
```

2\. 字符串拼接

由于字符串对象是不可变的，当多个字符串拼接到一起时，自然会产生不断复制建立新的字符串的问题。

```java
String a = ...;
String b = ...;
String c = ...;
String s = a + b + c;
```

当 a b c 三个字符串拼接到一起时，会先将 a 和 b 拼接在一起形成一个临时字符串对象，再将这个临时字符串对象和 c 拼接成 s，而这些临时对象的产生都伴随额外的内存分配和复制。为了避免这些不必要的临时对象，你应该使用 StringBuffer（同步）或 StringBuilder（非同步）拼接字符串。

```java
StringBuilder sb = new StringBuilder(a);
sb.append(b);
sb.append(c);
String s = sb.toString();
```

当然现代编译器会自动优化一些字符串拼接的情形，前面的例子会被编译器自动优化为如下形式：

```java
String s = new StringBuilder(a).append(b).append(c).toString();
```

但编译器只会处理一些简单的拼接，遇到一些分支判断或循环的情况则无法处理，我们仍需注意上述优化手段。

```java
String s = a + b;
if (x) {
    s += c;
}
```

3\. 必要的复制

封装是面向对象编程的重要概念。不幸的是，Java 为不小心打破封装提供了方便，Java 允许返回私有数据的引用。下面的代码揭示了这一点：

```java
import java.awt.Dimension;

/**
 * 示例类。高度和宽度的值不可以是负数。
 */
public class Example {
    private Dimension d = new Dimension(0, 0);

    /**
     * 设置高度和宽度。高度和宽度都必须为非负数，否则抛出异常。
     */
    public void setValues(int height, int width)
        throws IllegalArgumentException {
        if (height < 0 || width < 0) {
            throw new IllegalArgumentException();
        }
        d.height = height;
        d.width = width;
    }

    public Dimension getValues() {
        // 这里打破了封装
        return d;
    }
}
```

Example 类保证了它所存储的高度和宽度值永远为非负数，试图使用 setValues() 方法来设置负值会触发异常。不幸的是，由于 getValues() 返回 d 的引用，而不是 d 的副本，你可以编写如下的破坏性代码：

```java
Example ex = new Example();
Dimension d = ex.getValues();
d.height = -5;
d.width = -10;
```

现在，Example 对象拥有负值了！如果 getValues() 的调用者永远也不设置返回的 Dimension 对象的 width 和 height 值，那么仅凭测试是不可能检测到这类错误的。不幸的是，随着时间的推移，客户代码可能会改变返回的 Dimension 对象的值，这时追寻错误的根源是件枯燥且费时的事情，尤其是在多线程环境中。

更好的方式是让 getValues() 返回副本：

```java
public Dimension getValues() {
    return new Dimension(d.x, d.y);
}
```

现在，Example 对象的内部状态就安全了。调用者可以根据需要改变它所得到的副本的状态，但是要修改 Example 对象的内部状态，必须通过 setValues() 才可以。

4\. 不必要的复制

现在我们知道 get 方法应该返回内部数据对象的副本，而不是引用。但是，事情没有绝对：

```java
/**
 * 示例类。内部值不可以是负数。
 */
public class Example {
    private Integer i = 0;

    /**
     * 设置 i。x 必须为非负数，否则抛出异常。
     */
    public void setValues(int x) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException();
        }
        i = new Integer(x);
    }

    public Integer getValue() {
        // Integer 对象无法复制，所以我们通过这种方法复制。
        return new Integer(i.intValue());
    }
}
```

这段代码是安全的，但就象字符串复制的问题那样，又做了多余的工作。Integer 对象和 String 对象一样，是不可变对象。因此，返回内部的 Integer 对象，而不是它的副本，也是安全的。

```java
public Integer getValue() {
    return i;
}
```

Java 程序比 C++ 程序包含更多的不可变对象。JDK 所提供的常用不可变类包括：

* Boolean
* Byte
* Character
* Class
* Double
* Float
* Integer
* Long
* Short
* String
* Exception 的大部分子类

5\. 自编复制数组代码

Java 允许复制数组，但是开发者通常会多余地编写如下的代码，问题在于如下的三行循环代码，如果用 System.arraycopy() 方法一行就可以完成。

```java
int[] data = ...;
int[] copy = new int[data.length];
for (int i = 0; i < copy.length; ++i) {
    copy[i] = data[i];
}
```

这段代码是正确的，但是多余的。一个更好的实现是：

```java
int[] copy = new int[data.length];
System.arraycopy(data, 0, copy, 0, data.length);
```

如果你经常复制数组，编写一个工具方法是个好主意：

```java
static int[] cloneArray(int[] data) {
    int[] copy = new int[data.length];
    System.arraycopy(data, 0, copy, 0, data.length);
    return copy;
}
```

这样，我们的代码就更简洁了：

```java
int[] copy = cloneArray(data);
```

目前 Apache Commons-Lang 工具包已提供了这样的工具方法：

```java
import org.apache.commons.lang3.ArrayUtils;

int[] copy = ArrayUtils.clone(data);
```

在 JavaSE 6 中也提供了这样的工具方法：

```java
import java.util.Arrays;

int[] copy = Arrays.copyOf(data, data.length);
```

6\. 不完整的复制

有时候程序员知道必须返回一个副本，但是却不小心错误地复制了数据。由于仅仅做了部分的数据复制工作，下面的代码与程序员的意图有偏差：

```java
import java.awt.Dimension;
import java.util.Arrays;

/**
 * 示例类。高度和宽度的值不可以是负数。
 */
public class Example {
    public static final int TOTAL_VALUES = 10;

    private Dimension[] d = new Dimension[TOTAL_VALUES];

    /**
     * 设置高度和宽度。高度和宽度都必须为非负数，否则抛出异常。
     */
    public void setValues(int index, int height, int width)
        throws IllegalArgumentException {
        if (height < 0 || width < 0) {
            throw new IllegalArgumentException();
        }
        if (d[index] == null) {
            d[index] = new Dimension();
            d[index].height = height;
            d[index].width = width;
        }
    }

    public Dimension[] getValues() {
        return Arrays.copyOf(d, d.length);
    }
}
```

这里的问题是 getValues() 方法仅仅复制了数组，而没有复制数组中包含的 Dimension 对象。因此，虽然调用者无法改变内部的数组使其元素指向不同的 Dimension 对象，但是调用者却可以改变内部的数组元素（也就是 Dimension 对象）的内容。方法 getValues() 的更好版本为：

```java
public Dimension[] getValues() {
    Dimension[] copy = new Dimension[d.length];
    for (int i = 0; i < copy.length; ++i) {
        if (d[i] != null) {
            copy[i] = new Dimension(d[i].height, d[i].width);
        }
    }
    return copy;
}
```

在复制原子类型数据的多维数组的时候，也会犯类似的错误。原子类型包括 int, float 等。简单的复制 int 型的一维数组是正确的，如下所示：

```java
public void store(int[] input) {
    this.data = Arrays.copyOf(input, input.length);
    // OK
}
```

复制 int 型的二维数组更复杂些。Java 没有 int 型的二维数组，因此一个 int 型的二维数组实际上是一个这样的一维数组：它的元素类型为 ```int[]```。简单的复制 ```int[][]``` 型的数组会犯与上面例子中 getValues() 方法第一个版本同样的错误，因此应该避免这么做。下面的例子演示了在复制 int 型二维数组时错误的和正确的做法：

```java
public void wrongStore(int[][] input) {
    // Not OK!
    this.data = Arrays.copyOf(input, input.length);
}

public void rightStore(int[][] input) {
    // OK!
    this.data = new int[][input.length];
    for (int i = 0; i < data.length; ++i) {
        if (input[i] != null) {
            this.data[i] = Arrays.copyOf(input[i], input[i].length);
        }
    }
}
```

7\. 检查 new 操作的结果是否为 null

Java 编程新手有时候会检查 new 操作的结果是否为 null。可能的检查代码为：

```java
Integer i = new Integer(400);
if (i == null) {
    throw new NullPointerException();
}
```

这个检查是冗余的，if 和 throw 这两行代码完全是浪费，他们的唯一功用是让整个程序更臃肿，运行更慢。

C/C++ 程序员在开始写 Java 程序的时候常常会这么做，这是由于检查 C 中 malloc() 的返回结果是必要的，不这样做就可能产生错误。检查 C++ 中 new 操作的结果可能是一个好的编程行为，这依赖于异常是否被使能（许多编译器允许异常被禁止，在这种情况下 new 操作失败就会返回 null）。在 Java 中，new 操作不允许返回 null，如果真的返回 null，很可能是虚拟机崩溃了，这时候即便检查返回结果也无济于事。

8\. 使用 == 和 equals 方法

在 Java 中，有两种方式检查两个数据是否相等：使用 == 操作符，或者使用所有对象都实现的 equals() 方法。原子类型（int, long, char 等）不是对象，因此他们只能使用 == 操作符，如下所示：

```java
int x = 4;
int y = 5;
if (x == y) {
    ...
}
// if 语句无法编译
if (x.equals(y)) {
    ...
}
```

对象更复杂些，== 操作符检查两个引用是否指向同一个对象，而 equals 方法则实现更专门的相等性检查。

更显得混乱的是由 java.lang.Object 所提供的缺省 equals 方法的实现使用 == 来简单的判断被比较的两个对象是否为同一个。

许多类覆盖了缺省的 equals 方法，比如 String 类，它的 equals 方法检查两个 String 对象是否包含同样的字符串，而 Integer 的 equals 方法检查所包含的 int 值是否相等。

在检查两个对象是否相等的时候你应该使用 equals 方法，而对于原子类型的数据，你用该使用 == 操作符。

9\. 混淆原子操作和非原子操作

Java 保证读和写 32 位数或者更小的值是原子操作，也就是说可以在一步完成，因而不可能被打断，因此这样的读和写不需要同步。以下的代码是线程安全的：

```java
public class Example {
    private int value;

    public void set(int x) {
        // 线程安全
        this.value = x;
    }
}
```

不过，这个保证仅限于读和写，下面的代码不是线程安全的：

```java
public void increment() {
    // 这里相当于两到三个指令：
    // 1) 读取 value 的当前值
    // 2) 增加这个值
    // 3) 写回新的值
    ++this.value;
}
```

在测试的时候，你可能不会捕获到这个错误。首先，测试与线程有关的错误是很难的，而且很耗时间。其次，在有些机器上，这些代码可能会被翻译成一条指令，因此工作正常，只有当在其它的虚拟机上测试的时候这个错误才可能显现。因此最好在开始的时候就正确地同步代码：

```java
public synchronized void increment() {
    ++this.value;
}
```

10\. 在 catch 块中做清理工作

一段在 catch 块中做清理工作的代码如下所示：

```java
OutputStream os = null;
try {
    os = new OutputStream();
    // 在这里做些 os 相关的操作
    os.close();
} catch (Exception e) {
    if (os != null) {
        os.close();
    }
    throw e;
}
```

尽管这段代码在几个方面都是有问题的，但在测试中很容易漏掉这些问题。下面列出了这段代码所存在的三个问题：

1. 语句 os.close() 在两处出现，多此一举，而且会带来维护方面的麻烦。

2. 上面的代码仅仅处理了 Exception，而没有涉及到 Error。但是当 try 块运行出现了 Error，流也应该被关闭。

3. close() 可能会抛出异常。

上面代码的一个更优版本为：

```java
OutputStream os = null;
try {
    os = new OutputStream();
    // 在这里做些 os 相关的操作
} finally {
    if (os != null) {
        os.close();
    }
}
```

这个版本消除了上面所提到的两个问题：代码不再重复，Error 也可以被正确处理了。但是没有好的方法来处理第三个问题，也许最好的方法是把 close() 语句单独放在一个 try/catch 块中。

Java 7 提供了 try-with-resources 语法，可以使代码更简洁：

```java
try (OutputStream os = new OutputStream()) {
    // 在这里做些 os 相关的操作
}
```

11\. 增加不必要的 catch 块

一些开发者听到 try/catch 块这个名字后，就会想当然的以为所有的 try 块必须要有与之匹配的 catch 块。

C++ 程序员尤其会这样想，因为在 C++ 中不存在 finally 块的概念，而且 try 块存在的唯一理由只不过是为了与 catch 块相配对。

增加不必要的 catch 块的代码就象下面的例子，捕获到的异常又立即被抛出：

```java
try {
    ...
} catch(Exception e) {
    throw e;
} finally {
    ...
}
```

不必要的 catch 块被删除后，上面的代码就缩短为：

```java
try {
    ...
} finally {
    ...
}
```

12\. 没有正确实现 equals、hashCode 或 clone 等方法

方法 equals、hashCode 和 clone 由 java.lang.Object 提供的缺省实现是正确的。不幸地是，这些缺省实现在大部分时候毫无用处，因此许多类覆盖其中的若干个方法以提供更有用的功能。但是，当继承一个覆盖了若干个这些方法的父类的时候，子类通常也需要覆盖这些方法。在进行代码审查时，应该确保如果父类实现了 equals、hashCode 或 clone 等方法，那么子类也必须正确。正确的实现 equals、hashCode 和 clone 需要一些技巧。
