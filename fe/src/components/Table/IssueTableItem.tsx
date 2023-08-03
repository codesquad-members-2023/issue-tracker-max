import styled from "styled-components";
import { Icon } from "components/Common/Icon/Icon";
import { Tag } from "components/Common/Tag/Tag";
import UserTestProfile from "assets/img/profile_test.svg";

export const IssueTableItem = () => {
  return (
    <TableItem>
      <div>
        <CheckboxBox>
          <Icon icon="CheckBoxInitial" stroke="paletteBlue" />
        </CheckboxBox>
        <InfoBox>
          <TitleBox>
            <Icon icon="AlertCircle" stroke="paletteBlue" />
            <p>이슈 제목</p>
            <Tag
              text="document"
              color="nuetralBorderDefault"
              $backgroundColor="#FEFEFE"
              $border
              size="S"
            />
          </TitleBox>
          <SummaryBox>
            <span>#이슈번호</span>
            <span>#이슈번호</span>
            <span>작성자 및 타임스탬프 정보</span>
            <span>
              <Icon icon="Milestone" fill="nuetralTextDefault" />
              <p>마일스톤</p>
            </span>
          </SummaryBox>
        </InfoBox>
      </div>
      <AssigneesProfileBox>
        <ul>
          <li>
            <img src={UserTestProfile} alt="내 프로필 이미지" width={20} />
          </li>
          <li>
            <img src={UserTestProfile} alt="내 프로필 이미지" width={20} />
          </li>
        </ul>
      </AssigneesProfileBox>
    </TableItem>
  );
};

const TableItem = styled.li`
  display: flex;
  padding: 0px 32px;
  height: 96px;
  border: 1px solid black;
  justify-content: space-between;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-top: none;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};

  > div {
    display: flex;
    gap: 32px;
  }
`;

const CheckboxBox = styled.div`
  padding-top: 24px;
`;

const InfoBox = styled.div`
  display: flex;
  flex-direction: column;
  align-content: center;
  justify-content: center;
  gap: 8px;
  :last-child {
    display: flex;
    align-items: center;
    svg {
      margin-right: 8px;
    }
  }
`;

const TitleBox = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${({ theme: { font } }) => font.availableM20};
  color: ${({ theme: { color } }) => color.nuetralTextStrong};
`;

const SummaryBox = styled.div`
  font: ${({ theme: { font } }) => font.displayM16};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  display: flex;
  gap: 16px;
`;

const AssigneesProfileBox = styled.div`
  ul {
    list-style: none;
    display: flex;
    align-items: center;
    li {
      margin-right: -6px;
    }
    &:last-child {
      margin-right: 0;
    }
  }
`;
