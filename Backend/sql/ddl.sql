create table if not exists brand
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) not null,
    popularity int          not null,
    img_url    varchar(255) null,
    constraint UKrdxh7tq2xs66r485cc8dkxt77
        unique (name)
);

create table if not exists estimate
(
    id               bigint auto_increment
        primary key,
    amount           double       not null,
    expiry_time      datetime(6)  null,
    from_currency    varchar(255) null,
    rate             double       not null,
    result           double       not null,
    to_currency      varchar(255) null,
    converted_amount double       not null,
    exchange_rate    double       not null,
    original_amount  double       not null
);

create table if not exists member
(
    id       bigint       not null
        primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    role     varchar(255) null,
    userid   varchar(255) null,
    username varchar(255) not null,
    constraint UK6yhxjegychh1rq9jfynisnhro
        unique (userid),
    constraint UKmbmcqelty0fbrvxp1q58dn57t
        unique (email)
);

create table if not exists account
(
    id             bigint auto_increment
        primary key,
    account_name   varchar(255)                not null,
    account_number varchar(255)                not null,
    balance        decimal(38, 2) default 0.00 not null,
    is_default     bit                         not null,
    member_id      bigint                      null,
    constraint UK66gkcp94endmotfwb8r4ocxm9
        unique (account_number),
    constraint FKr5j0huynd7nsv1s7e9vb8qvwo
        foreign key (member_id) references member (id)
);

create table if not exists address
(
    id             bigint auto_increment
        primary key,
    city           varchar(255) not null,
    country        varchar(255) not null,
    is_default     bit          not null,
    recipient_name varchar(255) not null,
    state          varchar(255) not null,
    street_address varchar(255) not null,
    zip_code       varchar(255) not null,
    member_id      bigint       null,
    constraint FKcnw0s8hudme00qu71e3mqd5ih
        foreign key (member_id) references member (id)
);

create table if not exists card
(
    id               bigint auto_increment
        primary key,
    cardcvv          varchar(255) not null,
    card_expiry_date varchar(255) not null,
    card_holder_name varchar(255) not null,
    card_number      varchar(255) not null,
    is_default       bit          not null,
    member_id        bigint       null,
    constraint UKby1nk98m2hq5onhl68bo09sc1
        unique (card_number),
    constraint FKbf204t9qecurpbyoqlmpcy5t4
        foreign key (member_id) references member (id)
);

create table if not exists order_entity
(
    id             bigint auto_increment
        primary key,
    contact_email  varchar(255)                                                                                                 null,
    order_date     datetime(6)                                                                                                  null,
    payment_method tinyint                                                                                                      null,
    status         enum ('CANCELED', 'CREATED', 'DELIVERY_COMPLETED', 'ON_DELIVERY', 'PAYMENT_COMPLETED', 'PURCHASE_CONFIRMED') not null,
    total_amount   decimal(38, 2)                                                                                               null,
    address_id     bigint                                                                                                       null,
    member_id      bigint                                                                                                       not null,
    constraint FK8b36gxrma6uqxvgbcnduej3kt
        foreign key (member_id) references member (id),
    constraint FK8lvfdehnvu69xopspvaoqkik4
        foreign key (address_id) references address (id)
);

create table if not exists product
(
    id               bigint auto_increment
        primary key,
    category         enum ('HAIR_BODY', 'MAKEUP', 'SKINCARE', 'TOOLS_BRUSHES') not null,
    created_date     datetime(6)                                               not null,
    description      varchar(1000)                                             not null,
    ingredients      varchar(255)                                              not null,
    name             varchar(255)                                              not null,
    popularity       int                                                       not null,
    price            decimal(38, 2)                                            not null,
    brand_id         bigint                                                    not null,
    img_url          varchar(255)                                              null,
    product_code     varchar(255)                                              not null,
    discounted_price decimal(38, 2)                                            not null,
    constraint FKs6cydsualtsrprvlf2bb3lcam
        foreign key (brand_id) references brand (id)
);

create table if not exists cart
(
    id         bigint auto_increment
        primary key,
    quantity   int    null,
    member_id  bigint not null,
    product_id bigint not null,
    constraint FK3d704slv66tw6x5hmbm6p2x3u
        foreign key (product_id) references product (id),
    constraint FKix170nytunweovf2v9137mx2o
        foreign key (member_id) references member (id)
);

create table if not exists order_item
(
    id         bigint auto_increment
        primary key,
    price      decimal(38, 2) null,
    quantity   int            null,
    order_id   bigint         not null,
    product_id bigint         not null,
    constraint FK551losx9j75ss5d6bfsqvijna
        foreign key (product_id) references product (id),
    constraint FKkemfqcsngagxuetu5o1qnbyi5
        foreign key (order_id) references order_entity (id)
);

create table if not exists refresh_entity
(
    id         bigint auto_increment
        primary key,
    expiration varchar(255) null,
    refresh    varchar(255) null,
    username   varchar(255) null
);

create table if not exists review
(
    id         bigint auto_increment
        primary key,
    comment    varchar(255) not null,
    created_at datetime(6)  not null,
    rating     int          not null,
    member_id  bigint       not null,
    product_id bigint       not null,
    constraint FKiyof1sindb9qiqr9o8npj8klt
        foreign key (product_id) references product (id),
    constraint FKk0ccx5i4ci2wd70vegug074w1
        foreign key (member_id) references member (id)
);

create table if not exists review_reaction
(
    id            bigint auto_increment
        primary key,
    reaction_type enum ('DISLIKE', 'LIKE') null,
    member_id     bigint                   not null,
    review_id     bigint                   not null,
    constraint FKcj6w193lagh5wq2ss20k2tka5
        foreign key (review_id) references review (id),
    constraint FKfoo39t911ytqs7lq7tse8s5y8
        foreign key (member_id) references member (id)
);

create table if not exists transfer
(
    id                  bigint auto_increment
        primary key,
    amount              decimal(38, 2) not null,
    converted_amount    decimal(38, 2) not null,
    received_date       datetime(6)    not null,
    sent_date           datetime(6)    not null,
    receiver_account_id bigint         not null,
    sender_account_id   bigint         null,
    constraint FK36yxbg4litvecfsrd0llcc4uq
        foreign key (sender_account_id) references account (id),
    constraint FKlltfjm4ecosvcq79reei1x8hu
        foreign key (receiver_account_id) references account (id)
);

create table if not exists wishlist
(
    id         bigint auto_increment
        primary key,
    member_id  bigint not null,
    product_id bigint not null,
    constraint FKqchevbfw5wq0f4uqacns02rp7
        foreign key (product_id) references product (id),
    constraint FKr9m487rorwstnl1r1ib9r5pds
        foreign key (member_id) references member (id)
);