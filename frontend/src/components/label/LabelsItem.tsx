import { styled } from 'styled-components';
import Label from './Label';
import ButtonSmall from '../common/button/ButtonSmall';
import LabelItemProps from '../../types/LabelItemProps';
import { useState } from 'react';
import EditLabel from './EditLabel';

export default function LabelsItem({
  id,
  textColor,
  backgroundColor,
  name,
  description,
}: LabelItemProps) {
  const [isEdit, setIsEdit] = useState(false);

  return (
    <Container>
      <h4 className="blind">레이블 테이블 아이템</h4>
      {isEdit ? (
        <EditLabel
          {...{ id, textColor, backgroundColor, name, description }}
          cancel={() => setIsEdit(false)}
        />
      ) : (
        <>
          <LabelArea>
            <Label {...{ textColor, backgroundColor, name, description }} />
          </LabelArea>
          <Description>{description}</Description>
          <RightButton>
            <li>
              <ButtonSmall
                type="button"
                ghost
                flexible
                iconName="edit"
                onClick={() => setIsEdit(true)}>
                편집
              </ButtonSmall>
            </li>
            <li>
              <ButtonSmall type="button" ghost flexible iconName="trash">
                삭제
              </ButtonSmall>
            </li>
          </RightButton>
        </>
      )}
    </Container>
  );
}

const Container = styled.article`
  display: flex;
  align-items: center;
  gap: 32px;
  padding: 32px;
  background: ${({ theme }) => theme.color.neutral.surface.strong};
`;

const LabelArea = styled.div`
  width: 176px;
`;

const Description = styled.div`
  width: 870px;
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]}
`;

const RightButton = styled.menu`
  display: flex;
  gap: 24px;
  min-width: 106px;
  & > li {
    flex-grow: 1;
    & > button {
      width: 100%;
    }
    &:nth-child(2) > button {
      color: ${({ theme }) => theme.color.danger.text.default};
      path {
        stroke: ${({ theme }) => theme.color.danger.text.default};
      }

      rect {
        fill: ${({ theme }) => theme.color.danger.text.default};
      }
    }
  }
`;
