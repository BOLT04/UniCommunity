import React from 'react'

import { Comment as SUIComment, Rating } from 'semantic-ui-react'

import ReactMarkdown from 'react-markdown'

/**
 * Describes a Comment functional React component
 * @param {object} comment - The comment object. It must include the following:
 * {
 *     avatarSrc: string  -> This is the string with the source URL pointing to the avatar image. If it
 * isn't defined then a default image is used.
 *     author: string     -> This is the author's name.
 *     createdAt: string  -> This is the text that represents the date when the comment was created.
 * It must be already formatted, for example: Today at 5:42PM, or dd--MM-YYYY (02-10-2019).
 *     content: string    -> This represents the text content of the comment. It can come in markdown.
 *     replies: string[] -> An array containing the reply comments to this particular comment. 
 * It can be undefined (optional)
 * } 
 */
export const Comment = ({ comment: { avatarSrc, author, createdAt, content, replies }}) => (
    <>
        <SUIComment>
            <SUIComment.Avatar 
                src={avatarSrc ? avatarSrc : '/img/default-profile.png'} />
            <SUIComment.Content>
                <SUIComment.Author as='a'>{author}</SUIComment.Author>
                <SUIComment.Metadata>{createdAt}</SUIComment.Metadata>
                <SUIComment.Text>
                    <ReactMarkdown source={content} />
                </SUIComment.Text>
            </SUIComment.Content>
            {replies &&
                <SUIComment.Group>
                    {replies.map(comment => <Comment comment={comment} />)}            
                </SUIComment.Group>
            }
        </SUIComment>
    </>
)