package isel.pt.unicommunity.model.refactor_me_dad.test

import isel.pt.unicommunity.model.refactor_me_dad.mapper.IMapper

class TestMapper : IMapper<TestDto,TestModel> {

    override fun dtoToModel(dto: TestDto)=
            TestModel(dto.name)
}