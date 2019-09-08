import React from 'react'
import { Input, Form, TextArea, Button } from 'semantic-ui-react'

import BoardTemplate from './BoardTemplate'

export default function(props) {
  const {
    asyncRelativeFetch,
    onTitleChange,
    onDescriptionChange,
    boardTemplateApi,
    addToModules,
    updateHasForum,
    activateTemplate,
    createSubmitBtnOnClick
  } = props

  return (
    <>
      <h1 className='ui header'>Create a board</h1>
      <Form>
        <Form.Field required>
          <label>Title</label>
          <Input 
            name='title'
            onChange={onTitleChange} />   
        </Form.Field>
        <Form.Field>
          <label>Description</label>
          <TextArea 
            onChange={onDescriptionChange} 
            placeholder='Give more details about this board (optional)' />
        </Form.Field>
      </Form>
      
      <BoardTemplate
        api={boardTemplateApi}
        asyncRelativeFetch={asyncRelativeFetch}
        addToModules={addToModules}
        updateHasForum={updateHasForum}
        activateTemplate= {activateTemplate}
      />

      <Button
        content='Create' 
        primary 
        style={{marginTop: 10}} 
        onClick={createSubmitBtnOnClick}
      />
    </>
  )
}
