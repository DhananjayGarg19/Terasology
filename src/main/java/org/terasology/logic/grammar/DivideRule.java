package org.terasology.logic.grammar;

import org.terasology.math.Vector3i;

import java.util.ArrayList;
import java.util.List;

/**
 * The DivideRule is a complex shape rule that describes the division of its bounding box.
 * <p/>
 * The successor elements are calculated based on the geometric attributes of this shape.
 *
 * @author Tobias 'skaldarnar' Nett
 */
public class DivideRule extends ComplexRule {

    /**
     * The list of arguments of the _divide_ command.
     */
    private List<DivideArg> args;
    /**
     * The direction of the _divide_ command.
     */
    private Direction direction;

    /**
     * Defines the possible directions of a _divide_ command.
     */
    public enum Direction {
        X, Y, Z;
    }

    public DivideRule(List<DivideArg> args, Direction direction, float probability) {
        this(args, direction);
        this.probability = probability;
    }

    public DivideRule(List<DivideArg> args, Direction direction) {
        this.args = args;
        this.direction = direction;
    }

    /**
     * This method returns the successor elements of the _divide_ command this object is representing.
     * <p/>
     * The successor elements depend on the specific command and its direction, e.g. the following command
     * would have 2 successor elements, 'successor_1' and 'successor_2'. The scope (bounding box) of the
     * complex divide shape rule would be divided into 2 parts with the ratio of 0.7 to 0.3 along the Y
     * axis (vertical division).
     * <p/>
     * divide y {
     * [70%] successor_1
     * [30%] successor_2
     * }
     *
     * @return the successor elements the _divide_ command
     */
    public List<Shape> getElements() {
        // mutable variable for the new subdivisions dimension
        // Calculate the remaining size for relative values. (depending on the direction)
        int remainingSize = 0;
        switch (direction) {
            case X:
                remainingSize = dimension.x;
                break;
            case Y:
                remainingSize = dimension.y;
                break;
            case Z:
                remainingSize = dimension.z;
                break;
        }
        for (DivideArg arg : args) {
            if (arg.getSize().isAbsolute()) {
                remainingSize -= arg.getSize().getValue();
            }
        }
        // Preparing the return list.
        List<Shape> elements = new ArrayList<Shape>(args.size());
        Vector3i newPos = position;
        int newSize;

        for (DivideArg arg : args) {
            Shape s = arg.getShape();
            // Calculate the height of the new sub shape.
            if (arg.getSize().isAbsolute()) {
                newSize = (int) arg.getSize().getValue();
            } else {
                newSize = (int) (remainingSize * arg.getSize().getValue());
            }
            // set the position of the new shape ...
            s.setPosition(newPos);
            // Set _newPos_ to the next 'free' position and specify the new shape's dimension
            // (the value will only change for _direction_).
            switch (direction) {
                case X:
                    newPos.x += newSize;
                    s.setDimension(newSize, dimension.y, dimension.z);
                    break;
                case Y:
                    newPos.y += newSize;
                    s.setDimension(dimension.x, newSize, dimension.z);
                    break;
                case Z:
                    newPos.z += newSize;
                    s.setDimension(dimension.x, dimension.y, newSize);
                    break;
            }
            // Last, we set the shape's coordinate system properly…
            s.setCoordinateSystem(coordinateSystem);
            // …and add the newly created shape symbol to the successor elements list.
            elements.add(s);
        }
        return elements;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("divide ");
        builder.append(direction.name());
        builder.append(" { \n");
        for (DivideArg arg : args) {
            builder.append("\t ");
            builder.append(arg.toString());
            builder.append("\n");
        }
        builder.append("};");
        return builder.toString();
    }
}
