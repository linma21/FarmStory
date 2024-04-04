package kr.co.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -2045505939L;

    public static final QOrder order = new QOrder("order1");

    public final StringPath memo = createString("memo");

    public final NumberPath<Integer> orderNo = createNumber("orderNo", Integer.class);

    public final StringPath payment = createString("payment");

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath recaddr = createString("recaddr");

    public final StringPath rechp = createString("rechp");

    public final StringPath reciver = createString("reciver");

    public final StringPath status = createString("status");

    public final StringPath uid = createString("uid");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

