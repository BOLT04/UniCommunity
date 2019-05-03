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
 *     text: string       -> This represents whether or not the content is in markdown or not.
 * If it is then the proper component will be used to render it.
 *     replies: string[] -> An array containing the reply comments to this particular comment. 
 * It can be undefined (optional)
 * } 
 */
export const Comment = ({ comment: { avatarSrc, author, createdAt, text, replies }}) => (
    <>
        <SUIComment>
            <SUIComment.Avatar 
                src={avatarSrc ? avatarSrc : '/public/img/default-profile.png'} />
            <SUIComment.Content>
                <SUIComment.Author as='a'>{author}</SUIComment.Author>
                <SUIComment.Metadata>{createdAt}</SUIComment.Metadata>
                <SUIComment.Text>
                    <ReactMarkdown source={text} />
                </SUIComment.Text>
                <SUIComment.Actions>
                    <SUIComment.Action>Reply</SUIComment.Action>
                    <Rating icon='star' defaultRating={1} maxRating={4} />
                </SUIComment.Actions>
            </SUIComment.Content>
            {replies &&
                <SUIComment.Group>
                    {replies.map(comment => <Comment comment={comment} />)}            
                </SUIComment.Group>
            }
        </SUIComment>
    </>
)