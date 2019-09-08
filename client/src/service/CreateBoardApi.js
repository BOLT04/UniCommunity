import { asyncPostRequest } from '../common/common'

export default class CreateBoardApi  {

  createBoardAsync = async (url, name, description, fcmToken, { templateId, blackboardNames, hasForum }) => {
    // Construct request body
    const body = {
      name,
      description,
      fcmToken
    }

    if (templateId !== undefined)
      body.templateId = templateId
    else {
      body.blackboardNames = blackboardNames
      body.hasForum = hasForum
    }

    return await asyncPostRequest(url, body)
  }

  createCommentAsync = (url, content, isAnonymous) => {
    const body = { content, anonymous: isAnonymous }

    return asyncPostRequest(url, body)
  }
}
