package com.example.testapp.network.model

import com.example.testapp.network.model.books.BookNetwork
import com.example.testapp.network.model.books.BookRoom

fun NetworkResult.toRoomResult(): RoomResult {
    return RoomResult(
        networkAbstract,
        byline,
        created_date,
        item_type,
        kicker,
        material_type_facet,
        published_date,
        section,
        short_url,
        subsection,
        title,
        updated_date,
        url
    )
}

fun BookNetwork.toRoomResult(): BookRoom {
    return BookRoom(book_image, description, price, publisher, rank, title, author)
}