import { styled } from 'styled-components';
import Label from './Label';
import ButtonSmall from '../common/button/ButtonSmall';

export default function Labels({
  data,
}: {
  data: {
    id: number;
    textColor: string;
    backgroundColor: string;
    name: string;
    description: string;
  }[];
}) {
  return (
    <Container>
      <Header>{data.length}개의 레이블</Header>
      <Body>
        {data.map(({ id, textColor, backgroundColor, name, description }) => (
          <LabelsItem key={id}>
            <LabelArea>
              <Label {...{ textColor, backgroundColor, name, description }} />
            </LabelArea>
            <Description>{description}</Description>
            <RightButton>
              <li>
                <ButtonSmall type="button" ghost flexible iconName="edit">
                  편집
                </ButtonSmall>
              </li>
              <li>
                <ButtonSmall type="button" ghost flexible iconName="trash">
                  삭제
                </ButtonSmall>
              </li>
            </RightButton>
          </LabelsItem>
        ))}
      </Body>
    </Container>
  );
}
const Header = styled.h2`
  padding: 20px 32px;
  background: ${({ theme }) => theme.color.neutral.surface.default};
  border-bottom: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  ${({ theme }) => theme.font.display.bold[16]}
`;

const Body = styled.ul``;

const LabelsItem = styled.li`
  display: flex;
  align-items: center;
  gap: 32px;
  padding: 0 32px;
  height: 96px;
  border-bottom: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  background: ${({ theme }) => theme.color.neutral.surface.strong};
  &:last-child {
    border: 0;
  }
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

const Container = styled.article`
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  overflow: hidden;
  margin: auto;
`;
