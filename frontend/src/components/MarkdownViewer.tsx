import { ReactMarkdown } from "react-markdown/lib/react-markdown";
import remarkGfm from "remark-gfm";
import { styled } from "styled-components";

type MarkdownViewerProps = {
  markdown: string;
};

export function MarkdownViewer({ markdown }: MarkdownViewerProps) {
  return (
    <Div>
      <ReactMarkdown
        remarkPlugins={[remarkGfm]}
        components={{
          a: Anchor,
          code: InlineBlock,
          pre: Pre,
          blockquote: BlockQuote,
        }}
      >
        {markdown}
      </ReactMarkdown>
    </Div>
  );
}

function Anchor(props: { href?: string; children: React.ReactNode }) {
  return <a href={props.href}>{props.children}</a>;
}

function InlineBlock(props: { children: React.ReactNode }) {
  return <Code>{props.children}</Code>;
}

function Pre(props: { children: React.ReactNode }) {
  return <PreStyle>{props.children}</PreStyle>;
}

function BlockQuote(props: { children: React.ReactNode }) {
  return <BlockQuoteStyle>{props.children}</BlockQuoteStyle>;
}

const Div = styled.div`
  padding: 16px 24px 24px 24px;
  border-radius: ${({ theme }) =>
    `0px 0px ${theme.radius.large} ${theme.radius.large}`};
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.color.neutralTextDefault};
  background-color: ${({ theme }) => theme.color.neutralSurfaceStrong};

  h1 {
    font-size: 2em;
    margin: 0.67em 0;
  }
  h2 {
    font-size: 1.5em;
    margin: 0.75em 0;
  }
  h3 {
    font-size: 1.17em;
    margin: 0.83em 0;
  }
  h5 {
    font-size: 0.83em;
    margin: 1.5em 0;
  }
  h6 {
    font-size: 0.75em;
    margin: 1.67em 0;
  }
  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    font-weight: bolder;
  }

  menu,
  ul,
  ol {
    list-style: disc;
    padding-left: 2em;

  }
  
  .contains-task-list {
    list-style: none;
  }

  div,
  p,
  ul,
  ol,
  dl,
  table,
  pre,
  blockquote,
  code,
  form {
    margin-bottom: 16px;
  }

  * {
    max-width: 100%;
    box-sizing: border-box;
  }

  p {
    word-wrap: break-word;
  }
`;

const Code = styled.code`
  padding: 0.2em 0.4em;
  font-size: 85%;
  white-space: break-spaces;
  border-radius: 6px;
  background-color: ${({ theme }) => theme.color.neutralSurfaceBold};
`;

const PreStyle = styled.pre`
  font-family: monospace;
  padding: 16px;
  overflow: auto;
  line-height: 1.45;
  border-radius: 6px;
  background-color: ${({ theme }) => theme.color.neutralSurfaceBold};

  code {
    display: inline-block;
    padding: 0;
    margin: 0;
    overflow: visible;
    word-wrap: normal;
    word-break: normal;
    white-space: pre;
    background-color: transparent;
  }
`;

const BlockQuoteStyle = styled.blockquote`
  padding-left: 16px;
  color: ${({ theme }) => theme.color.neutralTextWeak};
  border-left: ${({ theme }) =>
    `4px solid ${theme.color.neutralBorderDefaultActive}`};
`;
