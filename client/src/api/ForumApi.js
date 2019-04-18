// TODO: this is a temporary API

const forumPostsDummy = {
    "collection": {
        "version" : "1.0",
        "href" : "http://localhost:3000/boards/1/forum/posts",
        "links" : [
            {"rel" : "home", "href" : "http://localhost:3000/home"},
            {"rel" : "board", "href" : "http://localhost:3000/boards/1"},
            {"rel" : "/rels/createPost", "href" : "http://localhost:3000/boards/1/forum/posts"}
        ],
        "items" : [
            {// smallForumTopic obj
                "href" : "http://localhost:3000/boards/1/forum/posts/1",
                "data" : [
                    {"name" : "title", "value" : "Anuncio 1", "prompt" : "?"},
                    {"name" : "id", "value" : "1", "prompt" : "?"},
                    {"name" : "smallDesc", "value" : "Exemplo de um anúncio....", "prompt" : "?"},
                    {"name TODO: como meter os recursos aqui" : "resources", "value" : [] , "prompt" : "?"},
                    {"name" : "author", "value" : "Paulo Pereira", "prompt" : "?"},
                    {"name" : "createdAt", "value" : "DAW", "prompt" : "?"}
                ],
                "links" : [
                    {"rel" : "self", "href" : "http://localhost:3000/boards/DAW/forum/1", "prompt" : "?"},
                    {"rel" : "up", "href" : "http://localhost:3000/boards/DAW/forum", "prompt" : "?" },
                    {"rel" : "menu", "href" : "http://localhost:3000/menu"}
                ]
            },
            {
                "href" : "http://localhost:3000/boards/1/forum/posts/2",
                "data" : [
                    {"name" : "title", "value" : "Post sobre HATEOAS", "prompt" : "?"},
                    {"name" : "id", "value" : "Sabem que isto é muita fixe certo...não??", "prompt" : "?"},
                    {"name" : "smallDesc", "value" : "Exemplo de um super post...só que nao", "prompt" : "?"},
                    {"name TODO: como meter os recursos aqui" : "resources", "value" : [] , "prompt" : "?"},
                    {"name" : "author", "value" : "Paulo", "prompt" : "?"},
                    {"name" : "createdAt", "value" : "DAW", "prompt" : "?"}
                ],
                "links" : [
                    {"rel" : "self", "href" : "http://localhost:3000/boards/DAW/forum/1", "prompt" : "?"},
                    {"rel" : "up", "href" : "http://localhost:3000/boards/DAW/forum", "prompt" : "?" },
                    {"rel" : "menu", "href" : "http://localhost:3000/menu"}
                ]
            }
        ]
    }
}

/**

 * Returns an array of Small Forum Posts response object. Format:
 * {
 *    title: string,
 *    smallDesc: string,
 *    author: string,
 *    createdAt: string
 * }
 * 
 * @param {string} url Represents the URL of the HTTP request to be made
 */
export default async function fetchForumPostsAsync(url) {
    return forumPostsDummy
}

let postId = 0

export async function createForumPostsAsync(url, title, content) {
    ++postId

    const postItem =  {
        "href" : `http://localhost:3000/boards/1/forum/posts/1${postId}`,
        "data" : [
            {"name" : "title", "value" : title, "prompt" : "?"},
            {"name" : "id", "value" : postId, "prompt" : "?"},
            {"name" : "smallDesc", "value" : `${content.substring(0, 10)}...`, "prompt" : "?"},
            {"name TODO: como meter os recursos aqui" : "resources", "value" : [] , "prompt" : "?"},
            {"name" : "author", "value" : "David", "prompt" : "?"},
            {"name" : "createdAt", "value" : new Date(), "prompt" : "?"}
        ],
        "links" : [
            {"rel" : "self", "href" : `http://localhost:3000/boards/1/forum/posts/1${postId}`, "prompt" : "?"},
            {"rel" : "up", "href" : "http://localhost:3000/boards/DAW/forum", "prompt" : "?" },
            {"rel" : "menu", "href" : "http://localhost:3000/menu"}
        ]
    }

    forumPostsDummy.collection.items.push(postItem)

    return {
        _links: {
            "self": { "href": `http://localhost:3000/boards/1/forum/posts/1${postId}` },
            "collection": { "href": "/api/boards/1/forum/posts" },
            "up": { "href": "/boards/1" }
        }
    }
}