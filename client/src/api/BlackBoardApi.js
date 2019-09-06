// TODO: this is a temporary API

const blackboardDummy = {
    "name" : "Sumários",
    "content": [
        {
            "name": "18/02/2019 - Course introduction",
            "text": "* Syllabus, teaching methodology and bibliography.\n  * [Evaluation](https://github.com/isel-leic-daw/1819v-public/wiki/evaluation)\n  * [Resources](https://github.com/isel-leic-daw/1819v-public/wiki/resources)"
        },
        {
            "name": "20/02/2019 - Designing Web APIs: Introduction ||| SEMM MARKDOWN ||| ",
            "text": "Web APIs (or HTTP APIs): Concept and Motivation"
        },
        {
            "name": "Com markdown boy",
            "text": `* Web APIs (or HTTP APIs): Concept and Motivation\n* The [Architecture of the World Wide Web](https://www.w3.org/TR/webarch/)\n* The HTTP protocol: Introduction\n* Documentation:\n  * ["Introduction to Web APIs"](https://github.com/isel-leic-daw/1819v-public/wiki/Web-APIs)\n  * ["Designing evolvable Web APIs: Chapter 1"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch01.html)`
        }
    ],
    "_links" : {
        "self": { "href": "/boards/1/blackboards/1" },
        "/rels/nav": { "href": "/" },
        "/rels/blackboards": { "href": "/boards/1/blackboards" },
        "/rels/board": { "href": "/boards/1" },
        "/rels/createBlackboardItem": { "href": "/boards/1/blackboards/1/items" } 
    }
}

/**

 * Returns a Blackboard response object. Format:
 * {
 *    name: string,
 *    content: array<Item>
 * }
 * 
 * Item: 
 * {name: string, text: string}
 * 
 * @param {string} url Represents the URL of the HTTP request to be made
 */
export default async function fetchBlackboardAsync(url) {
    return blackboardDummy
}