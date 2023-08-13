import { useState } from "react";
import styled from "styled-components";
import { TextInput } from "components/Common/Input/TextInput";
import { TextArea } from "components/Common/TextArea/TextArea";
import { Sidebar } from "components/Common/Sidebar/Sidebar";
import { Button } from "components/Common/Button/Button";
import { Icon } from "components/Common/Icon/Icon";
import { Tag } from "components/Common/Tag/Tag";
import { Comment } from "components/Common/Comment/Comment";
import UserTestProfile from "assets/img/profile_test.svg";

/* 임시 데이터 */
const commentLists = [
  {
    id: 1,
    author: "comment 작성자 이름",
    contents: "배가 너무너무 고파요",
    createdAt: "2023-07-25T10:11:12",
    file: "파일 url",
    profile: UserTestProfile,
  },
  {
    id: 2,
    author: "comment 작성자 이름",
    contents: "comment 내용",
    createdAt: "2023-07-25T11:22:33",
    file: "https://picsum.photos/300",
    profile: UserTestProfile,
  },
  // {
  //   id: 3,
  //   author: "comment 작성자 이름",
  //   contents: "comment 내용",
  //   createdAt: "2023-07-25T11:22:33",
  //   file: "https://picsum.photos/300",
  //   profile: UserTestProfile,
  // },
  // {
  //   id: 4,
  //   author: "comment 작성자 이름",
  //   contents: "comment 내용",
  //   createdAt: "2023-07-25T11:22:33",
  //   file: "https://picsum.photos/300",
  //   profile: UserTestProfile,
  // },
];

export const IssueDetailPage = () => {
  const [isEditingTitle, setIsEditingTitle] = useState(false);
  const [title, setTitle] = useState("FE 이슈트래커 디자인 시스템 구현");
  const [originalTitle, setOriginalTitle] = useState(title);

  const handleEditTitle = () => {
    setIsEditingTitle(true);
  };
  const handleInputChange = (newTItle: string) => {
    setTitle(newTItle);
    console.log(title);
  };

  const handleCancelEdit = () => {
    setTitle(originalTitle);
    setIsEditingTitle(false);
  };

  const handleTitleSubmit = () => {
    setOriginalTitle(title);
    setIsEditingTitle(false);
  };

  return (
    <Layout>
      <div>
        <HeaderTitleBox>
          {isEditingTitle ? (
            <TextInput
              $labelText="제목"
              $heightSize="S"
              value={title}
              onValueChange={handleInputChange}
              onBlur={handleTitleSubmit}
            />
          ) : (
            <h2>
              {title} <span>#2</span>
            </h2>
          )}
          {isEditingTitle ? (
            <div>
              <Button size="M" variant="outline" onClick={handleCancelEdit}>
                <Icon icon="Edit" size="M" stroke="brandTextWeak" />
                편집 취소
              </Button>
              <Button size="M" variant="outline" onClick={handleTitleSubmit}>
                <Icon icon="Archive" size="M" stroke="brandTextWeak" />
                편집 완료
              </Button>
            </div>
          ) : (
            <div>
              <Button size="M" variant="outline" onClick={handleEditTitle}>
                <Icon icon="Edit" size="M" stroke="brandTextWeak" />
                제목 편집
              </Button>
              <Button size="M" variant="outline">
                <Icon icon="Archive" size="M" stroke="brandTextWeak" />
                이슈 닫기
              </Button>
            </div>
          )}

          {/* <Button size="M" variant="outline" onClick={handleEditTitle}>
              <Icon icon="Edit" size="M" stroke="brandTextWeak" />
              제목 편집
            </Button>
            <Button size="M" variant="outline">
              <Icon icon="Archive" size="M" stroke="brandTextWeak" />
              이슈 닫기
            </Button> */}
        </HeaderTitleBox>

        <HeaderInfoBox>
          <Tag
            text="열린 이슈"
            icon="AlertCircle"
            stroke="brandTextDefault"
            color="#FEFEFE"
            $backgroundColor="#007AFF"
            size="M"
          />
          <span>이 이슈가 3분 전에 samsamis9님에 의해 열렸습니다</span>
          <span>∙</span>
          <span>코멘트 1개</span>
        </HeaderInfoBox>
      </div>

      <ContentBox>
        <CommentAreaBox>
          {commentLists.map((comment) => (
            <Comment key={comment.id} comment={comment}></Comment>
          ))}
          <div style={{ height: "184px" }}>
            <TextArea labelText="코멘트를 입력하세요" />
          </div>
          <Button size="S" variant="contained" disabled>
            <Icon icon="Plus" size="S" stroke="brandTextDefault" />
            코멘트 작성
          </Button>
        </CommentAreaBox>
        <SidebarAreaBox>
          <Sidebar></Sidebar>
          <Button size="S" variant="ghost" states="danger">
            <Icon icon="Trash" size="S" stroke="dangerTextDefault" />
            이슈 삭제
          </Button>
        </SidebarAreaBox>
      </ContentBox>
    </Layout>
  );
};

const Layout = styled.div`
  margin-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  height: 100%;
  padding-bottom: 80px;
`;
const HeaderTitleBox = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
  align-items: center;
  gap: 16px;

  > div {
    display: flex;
    gap: 16px;
  }

  > h2 {
    font: ${({ theme: { font } }) => font.displayB32};
    color: ${({ theme: { color } }) => color.nuetralTextStrong};

    span {
      color: ${({ theme: { color } }) => color.nuetralTextWeak};
    }
  }
`;
const HeaderInfoBox = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
`;
const ContentBox = styled.div`
  padding-top: 24px;
  border-top: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  display: flex;
  gap: 32px;
`;
const CommentAreaBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  flex: 1;

  > :last-child {
    margin-left: auto;
  }
`;

const SidebarAreaBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 16px;
`;
