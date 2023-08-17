import { styled } from 'styled-components';
import { useState } from 'react';
import Icons from '../../design/Icons';
import CheckBox from '../../constant/CheckBox';
import Button from '../common/button/BaseButton';
import DropdownIndicator from '../common/DropdownIndicator';
import { Issue } from '../../types/index';
import TableElement from './TableElement';

const { initial, active } = CheckBox;

export default function IssueTable({ issues }: { issues: Issue[] }) {
  const [totalCheckBox, setTotalCheckBox] = useState<CheckBox>(initial);

  function totalCheckBoxHandler(e: React.MouseEvent) {
    const input = e.target as HTMLInputElement;
    if (input.checked) {
      setTotalCheckBox(active);
      return;
    }
    setTotalCheckBox(initial);
  }

  return (
    <Container>
      <Title>
        <TotalCheckBox htmlFor="total-check">
          <CheckboxIcon checkBox={totalCheckBox} />
          <input
            onClick={totalCheckBoxHandler}
            type="checkbox"
            name="total-check"
            id="total-check"
          />
        </TotalCheckBox>
        <Buttons>
          <Left>
            <li>
              <Button type="button" iconName="alertCircle" flexible ghost>
                열린이슈
              </Button>
            </li>
            <li>
              <Button type="button" iconName="archive" flexible ghost>
                닫힌이슈
              </Button>
            </li>
          </Left>
          <Right>
            <li>
              <DropdownIndicator
                text="담당자"
                label="담당자 필터"
                elements={[]}
              />
            </li>
            <li>
              <DropdownIndicator
                text="레이블"
                label="레이블 필터"
                elements={[]}
              />
            </li>
            <li>
              <DropdownIndicator
                text="마일스톤"
                label="마일스톤 필터"
                elements={[]}
              />
            </li>
            <li>
              <DropdownIndicator
                text="작성자"
                label="작성자 필터"
                elements={[]}
              />
            </li>
          </Right>
        </Buttons>
      </Title>
      <Body>
        {issues ? (
          issues.map((issue, index) => (
            <Item key={index}>
              <TableElement {...issue} />
            </Item>
          ))
        ) : (
          <li>이슈가 없습니다</li>
        )}
      </Body>
    </Container>
  );
}

const Container = styled.article`
  background-color: ${({ theme }) => theme.color.neutral.surface.default};
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  overflow: hidden;
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  & > h3 {
    &:first-child {
      border-bottom: 1px solid
        ${({ theme }) => theme.color.neutral.border.default};
    }
    &:last-child {
      border-bottom: 0;
    }
  }
`;

const TotalCheckBox = styled.label`
  & > input {
    display: none;
  }
`;

function CheckboxIcon({ checkBox }: { checkBox: CheckBox }) {
  const Icon = Icons.checkBox[checkBox];
  return <Icon />;
}

const Title = styled.h3`
  padding: 16px 32px;
  display: flex;
  align-items: center;
  gap: 32px;
`;

const Buttons = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  & > ul {
    display: flex;
    gap: 24px;
    & > li {
      display: flex;
      align-items: center;
    }
  }
`;

const Left = styled.ul``;

const Right = styled.ul``;

const Body = styled.ul``;

const Item = styled.li`
  background: ${({ theme }) => theme.color.neutral.surface.strong};
  border-bottom: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme }) => theme.color.neutral.border.default};
  & > :last-child {
    border-bottom: 0;
  }
`;
