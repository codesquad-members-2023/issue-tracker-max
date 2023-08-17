import { styled } from 'styled-components';
import { Issue } from '../../types';
import CheckBox from '../../constant/CheckBox';
import Icons from '../../design/Icons';
import { useState } from 'react';
import Label from '../label/Label';

export default function TableElement({
  id,
  title,
  createdAt,
  user,
  labels,
  milestone,
}: Issue) {
  const [checked, setChecked] = useState(false);

  return (
    <Container>
      <Left>
        <CheckBoxLabel htmlFor={'issue' + id}>
          <input
            className="blind"
            type="checkBox"
            id={'issue' + id}
            onClick={() => setChecked((checked) => !checked)}
            checked={checked}
          />
          {(() => {
            const Icon = checked
              ? Icons.checkBox[CheckBox.active]
              : Icons.checkBox[CheckBox.initial];
            return <Icon />;
          })()}
        </CheckBoxLabel>
        <Info>
          <Title>
            <Icons.alertCircle /> {title}
            <Labels>
              {labels.map((label) => (
                <li key={label.id}>
                  <Label {...label} />
                </li>
              ))}
            </Labels>
          </Title>
          <Description>
            {`#${id}, ${user.name}님에 의해 작성되었습니다. `}
            {milestone && (
              <>
                <Icons.milestone />
                &nbsp;
                <span>{milestone.name}</span>
              </>
            )}
          </Description>
        </Info>
      </Left>
      <Right>
        <UserImage src={user.profileImg} alt={user.name} />
      </Right>
    </Container>
  );
}

const Container = styled.article`
  display: flex;
  padding: 0 32px;
  align-items: center;
`;
const Left = styled.div`
  display: flex;
  gap: 24px;
  width: 100%;
`;

const Right = styled.div`
  padding: 24px 0;
`;

const CheckBoxLabel = styled.label`
  padding: 24px 0;
  display: inline-block;
`;

const Info = styled.div`
  display: flex;
  padding: 16px 0;
  flex-direction: column;
  gap: 24px;
`;

const Title = styled.h3`
  display: flex;
  gap: 8px;
  align-items: center;
  ${({ theme }) => theme.font.available.medium[20]}
  & > svg {
    stroke: ${({ theme }) => theme.color.palette.blue};
  }
`;

const UserImage = styled.img`
  width: 16px;
  height: 16px;
  object-fit: cover;
  border-radius: ${({ theme }) => theme.objectStyles.radius.half};
`;

const Labels = styled.ul`
  display: flex;
  align-items: center;
  gap: 8px;
  line-height: 0;
`;

const Description = styled.p`
  ${({ theme }) => theme.font.available.medium[16]}
  color: ${({ theme }) => theme.color.neutral.text.weak};
`;
