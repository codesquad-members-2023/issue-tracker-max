import { styled } from "styled-components";
import { Button } from "../components/Button";
import { TextInput } from "../components/TextInput";

export function NewIssue() {
  return (
    <Div>
      <NewIssueHeader>새로운 이슈 작성</NewIssueHeader>
      <NewIssueBody>
        <img
          style={{ width: "32px" }}
          src="https://avatars.githubusercontent.com/u/41321198?v=4"
        />
        <Input>
          <TextInput size="L" placeholder="제목" label="제목" />
          <textarea name="" id=""></textarea>
        </Input>
        <Sidebar />
      </NewIssueBody>
      <NewIssueFooter>
        <Button size="M" buttonType="Ghost" icon="xSquare">
          작성 취소
        </Button>
        <Button size="L" buttonType="Container" disabled>
          완료
        </Button>
      </NewIssueFooter>
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  height: 818px;

  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const NewIssueHeader = styled.div`
  font: ${({ theme }) => theme.font.displayBold32};
  color: ${({ theme }) => theme.color.neutralTextStrong};
`;

const NewIssueBody = styled.div`
  display: flex;
  align-items: flex-start;
  gap: 24px;
  flex: 1 0 0;
  align-self: stretch;
`;

const NewIssueFooter = styled.div`
  align-self: flex-end;
  display: flex;
  align-items: center;
  gap: 32px;
`;

const Input = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  flex: 1 0 0;
  align-self: stretch;

  & div:first-child {
    width: 100%;
    align-self: stretch;

    & input {
      width: 100%;
    }
  }

  & textarea {
    flex: 1 0 0;
    align-self: stretch;
  }
`;

const Sidebar = styled.div`
  display: flex;
  width: 288px;
  flex-direction: column;
  align-items: flex-start;
  gap: 1px;
`;
