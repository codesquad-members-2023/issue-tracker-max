import { useContext, useEffect, useState } from 'react';
import { styled } from 'styled-components';
import DropdownIndicator from './DropdownIndicator';
import Icons from '../../design/Icons';
import React from 'react';
import Option from '../../constant/Option';
import { AppContext } from '../../main';
import DropdownType from '../../constant/DropdownType';

export default function FilterBar({ baseKeyword = 'status:open' }) {
  const [keyword, setKeyword] = useState(baseKeyword);
  const { util } = useContext(AppContext);
  const filter = util.getFilter() as (keyword: string) => void;

  useEffect(() => {
    filter(keyword);
  }, [keyword]);

  function submitHandler(e: React.FormEvent) {
    e.preventDefault();
    filter(keyword);
  }

  return (
    <Container>
      <h3 className="blind">필터바</h3>
      <StyledDropdownIndicator
        type={DropdownType.state}
        text="필터"
        label="이슈 필터"
        elements={[
          {
            type: DropdownType.state,
            text: '열린 이슈',
            option: Option.Selected,
          },
          {
            type: DropdownType.state,
            text: '내가 작성한 이슈',
            option: Option.Available,
          },
          {
            type: DropdownType.state,
            text: '나에게 할당된 이슈',
            option: Option.Available,
          },
          {
            type: DropdownType.state,
            text: '내가 댓글을 남긴 이슈',
            option: Option.Available,
          },
          {
            type: DropdownType.state,
            text: '닫힌 이슈',
            option: Option.Available,
          },
        ]}
      />
      <TextFilter onSubmit={submitHandler}>
        <Icons.search />
        <TextInput
          id="filter"
          name="filter"
          value={keyword}
          onChange={(e) => {
            const input = e.target as HTMLInputElement;
            setKeyword(input.value);
          }}></TextInput>
      </TextFilter>
    </Container>
  );
}

const Container = styled.section`
  width: 560px;
  display: flex;
  align-items: center;
  border: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium};
  background: ${({ theme }) => theme.color.neutral.surface.bold};

  &:focus-within {
    border-color: ${({ theme }) => theme.color.neutral.border.active};
    background: ${({ theme }) => theme.color.neutral.surface.strong};
  }

  &:focus > form > input {
    color: ${({ theme }) => theme.color.neutral.text.default};
  }
`;

const StyledDropdownIndicator = styled(DropdownIndicator)`
  height: 40px;
  background: ${({ theme }) => theme.color.neutral.surface.default};
  border-right: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium} 0 0
    ${({ theme }) => theme.objectStyles.radius.medium};

  & > button {
    padding: 8px 24px;
    & > span > span {
      display: inline-block;
      width: 60px;
      text-align: left;
    }
  }
`;

const TextFilter = styled.form`
  height: 40px;
  padding: 0 24px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
  flex: 1 0 0;
`;

const TextInput = styled.input`
  width: 100%;
  border: none;
  outline: none;
  background: transparent;
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]};
`;
